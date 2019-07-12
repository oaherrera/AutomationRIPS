package rips.page;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import control.elementos.ObjetosConfigAux;

public class FormGestionCuentas {

	ObjetosConfigAux objAux;
	FormValidarRips objFVRips;

	By optGestionVuentasValidadas = By.xpath("//span[contains(text(),'Gestión de cuentas validadas')]");
	By txtNumeroCuenta = By.xpath("(//input[contains(@id,'formTablaCuentas:tablaCuentas:')])[1]");
//	By btnBuscar = By.xpath("(//button[contains(@id,'formBuscarCuentas:')])[1]");
	By tbodyRegistro = By.xpath("//*[@id='formTablaCuentas:tablaCuentas_data']");

	public FormGestionCuentas(ObjetosConfigAux objAux, FormValidarRips objFVRips) {
		this.objAux = objAux;
		this.objFVRips = objFVRips;
	}

	public void clicOptGestionVuentasValidadas() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), optGestionVuentasValidadas);
		objAux.getDriver().findElement(optGestionVuentasValidadas).click();
	}

	public void setTxtNumeroCuenta() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), txtNumeroCuenta);
		objAux.getDriver().findElement(txtNumeroCuenta).sendKeys(String.valueOf(objFVRips.getNumeroCuenta()));
		objAux.getDriver().findElement(txtNumeroCuenta).sendKeys(Keys.ENTER);
	}

//	public void clicBtnBuscar() throws IOException {
//		objAux.EsperaElemento(objAux.getDriver(), btnBuscar);
//		objAux.getDriver().findElement(btnBuscar).click();
//		objAux.AdminDocPdf.generaEvidencia("Se realiza consulta de registro de RIPS",
//				Shutterbug.shootPage(objAux.getDriver()).getImage());
//	}

	public void validarTbodyRegistro() throws IOException {
		if (!(objAux.getDriver().findElement(tbodyRegistro).getText() == "No se encontraron registros")) {
			objAux.AdminDocPdf.generaEvidencia("Existe el registro",
					Shutterbug.shootPage(objAux.getDriver()).getImage());
		}
	}

	public void consultar() throws IOException {
		clicOptGestionVuentasValidadas();
		setTxtNumeroCuenta();
//		clicBtnBuscar();
		validarTbodyRegistro();
	}

}