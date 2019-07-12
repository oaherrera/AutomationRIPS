package ejecucion;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.xml.*;
import com.beust.jcommander.JCommander;

public class Main {

	@SuppressWarnings("rawtypes")
	static List<Class> result = new ArrayList<Class>();

	static Map<Object, Object> FlujosCP = new TreeMap<Object, Object>();

	// @args[0] = Nombre del caso de prueba a ejecutar. Si es "all" se
	// ejecutaran todos los casos del proyecto.
	// @args[1] = Especifica el navegador: 1.Internet Explorer, 2.Chrome,
	// 3.Mozila Firefox, 4.Edge, 5.Safari, 6.Opera
	// @args[2] = Ambientes: Produccion, Precol, Proyecto.

	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		if (args.length == 0 || args.length > 3) {
			System.out.println("Cantidad de parametros no corresponde con el esperado");
		}

		List<XmlClass> xmlClases = new ArrayList<XmlClass>();
		obtenerClases(new File(".").getCanonicalFile());

		outerloop: for (Class c : result) {
			if (args[0].contains("ALL")) {
				Properties prop = new Properties();
				InputStream entrada = new FileInputStream("Config.properties");
				prop.load(entrada);
				String clases = prop.getProperty("clases");
				String[] clase = clases.split(",");
				for (String a : clase) {
					if (c.getName().contains(a)) {
						System.out.println(a);
						xmlClases.add(new XmlClass(c));
						break;
					}
				}
			} else {
				Method[] m = c.getMethods();
				for (int i = 0; i < m.length; i++) {
					if (m[i].isAnnotationPresent(Test.class)) {
						String var = c.getName();
						String[] variable = var.split("test");
						@SuppressWarnings("unused")
						String variable1 = variable[0];
						String variable2 = variable[1];
						variable2 = variable2.substring(1);
						if (args[0].contains("CLASS:")
								&& variable2.equals(args[0].substring(args[0].indexOf(":") + 1))) {
							System.out.println(variable2);
							xmlClases.add(new XmlClass(c));
							break outerloop;
						} else if (args[0].contains("CP:")
								&& m[i].getName().equals(args[0].substring(args[0].indexOf(":") + 1))) {
							System.out.println(m[i].getName());
							xmlClases.add(new XmlClass(c));
							break outerloop;
						}
					}
				}
			}
		}

		System.out.println("");

		if (xmlClases.size() > 0) {
			ejecutarTests(xmlClases, args);
		} else {
			System.out.println("No se ha identificado el objeto solicitado.");
		}
	}

	public static void obtenerClases(File dir) throws ClassNotFoundException, IOException {
		File listFile[] = dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					obtenerClases(listFile[i]);
				} else {
					if (listFile[i].getName().endsWith(".class")) {
						int posInicial = listFile[i].getAbsolutePath().indexOf("bin\\") + 4;
						String nombrePackage = listFile[i].getAbsolutePath().substring(posInicial).replace("\\", ".");
						result.add(Class.forName(nombrePackage.substring(0, nombrePackage.length() - 6)));
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void ejecutarTests(List<XmlClass> pXmlClases, String[] args) {
		CommandLineArgs options = new CommandLineArgs();
		JCommander jCommander = new JCommander(options);
		XmlSuite suite = new XmlSuite();
		List<XmlInclude> include = new ArrayList<XmlInclude>();
		Map parametros = new HashMap<String, Object>();
		XmlTest test = new XmlTest(suite);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		TestNG testNG = new TestNG();

		parametros.put("EjecutaConfig", "True");
		parametros.put("CierraSesion", "True");

		if (args[0].contains("ALL")) {
			for (int i = 0; i <= pXmlClases.size() - 1; i++) {
				suite = new XmlSuite();
				test = new XmlTest(suite);
				suites = new ArrayList<XmlSuite>();
				testNG = new TestNG();

				if (i > 0) {
					parametros.replace("EjecutaConfig", "False");
				}

				if (i == pXmlClases.size() - 1) {
					parametros.replace("CierraSesion", "True");
				} else {
					parametros.replace("CierraSesion", "False");
				}
				suite.setParameters(parametros);

				test.setName("EjecucuionAll");

				suite.setName("EjecucionAutomatica" + i);
				List<XmlClass> xmlClase = new ArrayList<XmlClass>();
				xmlClase.add(pXmlClases.get(i));
				test.setXmlClasses(xmlClase);

				suites.add(suite);

				testNG.setXmlSuites(suites);
				testNG.run();
			}
		} else {
			parametros.replace("CierraSesion", "True");

			suite.setParameters(parametros);
			suite.setName("EjecucionAutomatica");
			if (args[0].contains("CP:")) {
				include.add(new XmlInclude(args[0].substring(args[0].indexOf(":") + 1)));
				pXmlClases.get(0).setIncludedMethods(include);
			}

			test.setName(args[0].substring(args[0].indexOf(":") + 1));
			test.setXmlClasses(pXmlClases);

			suites.add(suite);

			testNG.setXmlSuites(suites);
			testNG.run();
		}
		if (testNG.hasFailure()) {
			System.exit(3);
		} else {
			System.exit(0);
		}
	}

}