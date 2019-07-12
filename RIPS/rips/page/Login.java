package rips.page;

import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.Assert;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import control.elementos.ObjetosConfigAux;

public class Login {

	ObjetosConfigAux objAux;

	By txtUserName = By.id("username");
	By txtPassword = By.id("password");
	By btnLogin = By.id("Continuar");
	By btnCerrarSesion = By.xpath("//*[@id='frmLogOut']/a");

	/* Metodo Constructor */
	public Login(ObjetosConfigAux objAux) throws IOException {
		this.objAux = objAux;
	}

	// Metodos de eventos y acciones
	public void setTxtUserName(String pUserName) throws IOException {
		if (objAux.EsperaElemento(objAux.getDriver(), txtUserName)) {
			objAux.getDriver().findElement(txtUserName).sendKeys(pUserName);
		}
	}

	public void setTxtPassword(String pPassword) throws IOException {
		if (objAux.EsperaElemento(objAux.getDriver(), txtPassword)) {
			objAux.getDriver().findElement(txtPassword).sendKeys(pPassword);
		}
	}

	public void clicBtnLogin() throws IOException {
		objAux.AdminDocPdf.generaEvidencia("Se ingresan credenciales", Shutterbug.shootPage(objAux.getDriver()).getImage());
		objAux.EsperaElemento(objAux.getDriver(), btnLogin);
		objAux.getDriver().findElement(btnLogin).click();
	}

	public void clicBtnCerrarSesion() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnCerrarSesion);
		objAux.getDriver().findElement(btnCerrarSesion).click();
		objAux.AdminDocPdf.generaEvidencia("Se cierra sesión", Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void iniciarSesion(String pUser, String pPassword) throws IOException {
		setTxtUserName(pUser);
		setTxtPassword(pPassword);
		clicBtnLogin();
	}

	public void cerrarSesion() throws IOException, InterruptedException {
		clicBtnCerrarSesion();
		objAux.EsperaElemento(objAux.getDriver(), btnLogin, 3);
		Assert.assertTrue(objAux.getDriver().findElement(btnLogin).isDisplayed());
	}

}