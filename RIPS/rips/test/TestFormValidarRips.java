package rips.test;

import java.io.*;
import java.net.MalformedURLException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.lowagie.text.DocumentException;
import control.elementos.ObjetosConfigAux;
import evidencia.doc.pdf.AdminDocPdf;
import manager.param.AdminParam;
import model.*;
import rips.page.*;

public class TestFormValidarRips {

	ObjetosConfigAux objAux;
	Navegadores navegador;
	Login objLogin;
	FormValidarRips objFormValidarRips;
	FormGestionCuentas objFormGestionCuentas;
	Ambientes ambiente = Ambientes.PROYECTOS;
	int count;

	@BeforeSuite
	public void setup() throws IOException, InterruptedException {
		objAux = new ObjetosConfigAux("2");
		objLogin = new Login(objAux);
		objFormValidarRips = new FormValidarRips(objAux);
		objFormGestionCuentas = new FormGestionCuentas(objAux, objFormValidarRips);
		objAux.getDriver().get(objAux.obtenerValorProperties("url"));
	}

	@Test(priority = 2)
	public void ingresoDatosValidarRips() throws Exception {
		objAux.AdminDocPdf = new AdminDocPdf(Ambientes.PROYECTOS, Navegadores.CHROME, DispositivoPrueba.WEB);
		objAux.AdminParam = new AdminParam(TipoCone.EXCEL, "datos_formulario");
		objAux.AdminParam.ObtenerParametros();
		objLogin.iniciarSesion(objAux.getUsuario(), objAux.getContrasenia());
		objFormValidarRips.ingresoDatosForm(objAux.buscaElementoParametro("Sede_Ips"),
				objAux.buscaElementoParametro("Mes"), objAux.buscaElementoParametro("Anio"),
				objAux.buscaElementoParametro("Tipo_Servicio"), objAux.buscaElementoParametro("Responsable_Pago"),
				objAux.buscaElementoParametro("Regional"));
		objFormGestionCuentas.consultar();
	}

	@AfterMethod
	public void finalizeTest(ITestResult t) throws MalformedURLException, DocumentException, IOException {
		if (t.getStatus() == ITestResult.SUCCESS)
			objAux.AdminDocPdf.crearDocumento(Estados.SUCCESS);
		else {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			Throwable cause = t.getThrowable();
			if (null != cause) {
				cause.printStackTrace(pw);
				objAux.AdminDocPdf.generaEvidencia(
						"Resultado NO Esperado: "
								+ sw.getBuffer().toString().substring(0, sw.getBuffer().toString().indexOf("at ")),
						Shutterbug.shootPage(objAux.getDriver()).getImage());
			} else {
				objAux.AdminDocPdf.generaEvidencia("Resultado NO Esperado: ",
						Shutterbug.shootPage(objAux.getDriver()).getImage());
			}
			objAux.AdminDocPdf.crearDocumento(Estados.FAILED);
		}
	}

	@AfterClass
	public void driverQuit() throws IOException, InterruptedException {
		objLogin.cerrarSesion();
		objAux.getDriver().quit();
	}

}