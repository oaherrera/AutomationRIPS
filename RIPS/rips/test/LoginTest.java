package rips.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.lowagie.text.DocumentException;

import control.elementos.ObjetosConfigAux;
import evidencia.doc.pdf.AdminDocPdf;
import manager.param.AdminParam;
import model.Ambientes;
import model.DispositivoPrueba;
import model.Estados;
import model.Navegadores;
import model.TipoCone;
import rips.page.Login;

public class LoginTest {

	ObjetosConfigAux objAux;

	Navegadores navegador;
	Login objLogin;
	Ambientes ambiente = Ambientes.PROYECTOS;
	int count = 0;

	@BeforeSuite
	public void setup() throws IOException, InterruptedException {
		objAux = new ObjetosConfigAux("2");
		this.navegador = objAux.getNavegador();
		objLogin = new Login(objAux);

	}

	@Test
	public void IngresarDatos() throws IOException, InterruptedException {
		objAux.AdminDocPdf = new AdminDocPdf(Ambientes.PROYECTOS, Navegadores.CHROME, DispositivoPrueba.WEB);
		objAux.AdminParam = new AdminParam(TipoCone.EXCEL, "login");
		objAux.AdminParam.ObtenerParametros();
		/*
		 * objAux.AdminDocPdf.generaEvidencia( "Evidencia #" + count++ + "-  " +
		 * (objAux.buscaElementoParametro("Usuario").toString()),
		 * Shutterbug.shootPage(objAux.getDriver()).getImage());
		 */

		objLogin.iniciarSesion(objAux.buscaElementoParametro("Usuario").toString(),
				objAux.buscaElementoParametro("Password").toString());

		// Thread.sleep(2000);

		/*
		 * objAux.AdminDocPdf.generaEvidencia( "Evidencia #" + count++ + "-  " +
		 * (objAux.buscaElementoParametro("Usuario").toString()),
		 * Shutterbug.shootPage(objAux.getDriver()).getImage());
		 */

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
	public void driverQuit() {
	objAux.getDriver().quit();
	}

}

