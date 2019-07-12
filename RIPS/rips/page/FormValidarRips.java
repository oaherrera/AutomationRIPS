package rips.page;

import java.io.*;
import java.util.*;
import org.openqa.selenium.By;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import control.elementos.ObjetosConfigAux;

public class FormValidarRips {

	ObjetosConfigAux objAux;
	private int numeroCuenta = 0;
	private int valorRegistros = 0;

	By optCuenta = By.xpath("(//label[contains(text(),'Cuenta')])[1]");
	By optValidarRips = By.xpath("//span[contains(text(),'Validar RIPS')]");
	By txtSedeIps = By.xpath("//input[contains(@id,'formValidarRips:sedeIps_input')]");
	By optSede = By.xpath("//div[contains(@id,'formValidarRips:sedeIps_panel')]");
	By lstMesPrestacion = By.xpath("//label[contains(@id,'formValidarRips:mesPrestacion_label')]");
	By optMes = By.xpath("//li[contains(@id,'formValidarRips:mesPrestacion_5')]");
	By lstAnio = By.xpath("//label[contains(@id,'formValidarRips:anioPrestacion_label')]");
	By optAnio = By.xpath("//li[contains(@id,'formValidarRips:anioPrestacion_1')]");
	By txtNumeroCta = By.xpath("//input[contains(@id,'formValidarRips:noCuenta')]");
	By txtValorCuenta = By.xpath("//*[@id='formValidarRips:valorCuenta']/input[@type='text']");
	By btnContrato = By.xpath("//*[@id='formValidarRips:btnBuscarContrato']");
	By btnPreultimoContrato = By
			.xpath("//*[contains(@id,':formBuscarContrato:tableBusquedaContratos_data')]/tr[9]/td[6]/button");
	By listTipoServicio = By.xpath("//label[contains(@id,'formValidarRips:tipoServicioMenu_label')]");
	By listResponsablePago = By.xpath("//label[contains(@id,'formValidarRips:responsablePagoMenu_label')]");
	By listRegional = By.xpath("//label[contains(@id,'formValidarRips:regionalMenu_label')]");
	By fileAdjuntarArchivos = By.xpath("//input[@accept='txt|zip|TXT|ZIP']");
	By btnCargarArchivo = By.xpath("(//div[@class='text-right']/button[contains(@id,'formValidarRips:')])[1]");
	By btnSi = By.xpath("(//div[contains(@class,'ui-dialog-buttonpane')]/button[contains(@id,'formValidarRips:')])[1]");
	By btnAceptar = By
			.xpath("(//div[contains(@class,'ui-dialog-buttonpane')]/button[contains(@id,'formValidarRips:')])[3]");
	By btnConfirmar = By.xpath("//button[contains(@id,'formValidarRips:btnConfirmar')]");
	By lblNumeroCuenta = By.xpath("//*[@id='formValidarRips:resultadoRadicacion']/tbody/tr[2]/td[2]");
	By btnSiConfirmarCargue = By
			.xpath("(//div[contains(@class,'ui-dialog-buttonpane')]/button[contains(@id,'formValidarRips:')])[4]");
	By btnCerrar = By.xpath(
			"//div[contains(@id,'formValidarRips:cdConfirmacionPreauditoria')]//button[contains(@id,'formValidarRips:')]");

	public FormValidarRips(ObjetosConfigAux objAux) {
		this.objAux = objAux;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void clicOptCuenta() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), optCuenta);
		objAux.getDriver().findElement(optCuenta).click();
	}

	public void clicOptValidarRips() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), optValidarRips);
		objAux.getDriver().findElement(optValidarRips).click();
	}

	public void setTxtSedeIps(String pSedeIps) throws IOException, InterruptedException {
		objAux.EsperaElemento(objAux.getDriver(), txtSedeIps);
		objAux.getDriver().findElement(txtSedeIps).sendKeys(pSedeIps);
	}

	public void clicOptSede() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), optSede);
		objAux.getDriver().findElement(optSede).click();
	}

	public void seleccionarMes(String pMes) throws Exception, IOException {
		if (objAux.EsperaElemento(objAux.getDriver(), lstMesPrestacion)) {
			objAux.SelecOpcList(pMes, objAux.IrAlElemento(objAux.traerElemento(lstMesPrestacion)),
					objAux.getDriver().findElements(optMes));
			Thread.sleep(1000);
		}
	}

	public void seleccionarAnio(String pAnio) throws Exception, IOException {
		if (objAux.EsperaElemento(objAux.getDriver(), lstAnio)) {
			objAux.SelecOpcList(pAnio, objAux.IrAlElemento(objAux.traerElemento(lstAnio)),
					objAux.getDriver().findElements(optAnio));
			Thread.sleep(1000);
		}
	}

	public void sendNumeroCuenta() throws IOException {
		numeroCuenta = (int) (Math.random() * 1000000);
		objAux.EsperaElemento(objAux.getDriver(), txtNumeroCta);
		objAux.getDriver().findElement(txtNumeroCta).sendKeys(Integer.toString(numeroCuenta));
	}

	public void setTxtValorCuenta() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), txtValorCuenta);
		File entrada = new File(System.getProperty("user.dir"));
		File directory[] = entrada.listFiles();
		outloop: for (int i = 0; i <= directory.length; i++) {
			if (directory[i].isDirectory() && directory[i].getName().contains("actura")) {
				File files[] = directory[i].getCanonicalFile().listFiles();
				for (int j = 0; j <= files.length; j++) {
					if (files[j].getName().contains("AF") && files[j].getName().endsWith(".txt")) {
						FileReader read = new FileReader(files[j]);
						BufferedReader bRead = new BufferedReader(read);
						int valorTotal = 0;
						String linea = "";
						while ((linea = bRead.readLine()) != null) {
							if (linea.contains(",,,,")) {
								String arrayLinea[] = linea.split(",,,,");
								valorTotal = valorTotal + Integer.parseInt(arrayLinea[1].replace(".00", ""));
							}
						}
						bRead.close();
						read.close();
						objAux.getDriver().findElement(txtValorCuenta).sendKeys(String.valueOf(valorTotal));
						break outloop;
					}
				}
			}
		}
	}

	public void clicBtnContrato() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnContrato);
		objAux.getDriver().findElement(btnContrato).click();
	}

	public void clicBtnPreultimoContrato() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnPreultimoContrato);
		objAux.getDriver().findElement(btnPreultimoContrato).click();
	}

	public void selectListTipoServicio(String pTipoServicio) throws IOException, InterruptedException {
		if (objAux.EsperaElemento(objAux.getDriver(), listTipoServicio)) {
			objAux.SelecOpcList(pTipoServicio, objAux.IrAlElemento(objAux.traerElemento(listTipoServicio)), objAux
					.getDriver().findElements(By.xpath("//li[contains(@id,'formValidarRips:tipoServicioMenu')]")));
			Thread.sleep(1000);
		}
	}

	public void selectListResponsablePago(String pResponsablePago) throws IOException, InterruptedException {
		if (objAux.EsperaElemento(objAux.getDriver(), listResponsablePago)) {
			objAux.SelecOpcList(pResponsablePago, objAux.IrAlElemento(objAux.traerElemento(listResponsablePago)), objAux
					.getDriver().findElements(By.xpath("//li[contains(@id,'formValidarRips:responsablePagoMenu')]")));
			Thread.sleep(1000);
		}
	}

	public void selectListRegional(String pRegional) throws IOException, InterruptedException {
		if (objAux.EsperaElemento(objAux.getDriver(), listRegional)) {
			objAux.SelecOpcList(pRegional, objAux.IrAlElemento(objAux.traerElemento(listRegional)),
					objAux.getDriver().findElements(By.xpath("//li[contains(@id,'formValidarRips:regionalMenu')]")));
			Thread.sleep(1000);
			objAux.AdminDocPdf.generaEvidencia("Se diligencia formulario ",
					Shutterbug.shootPage(objAux.getDriver()).getImage());
		}
	}

	public void selectFileAdjuntarArchivos() throws IOException {
		File entrada = new File(System.getProperty("user.dir"));
		File directory[] = entrada.listFiles();
		List<String> arrayFiles = new ArrayList<String>();
		for (int i = 0; i < directory.length; i++) {
			if (directory[i].isDirectory() && directory[i].getName().contains("actura")) {
				File files[] = directory[i].getCanonicalFile().listFiles();
				for (int j = 0; j < files.length; j++) {
					if (!files[j].getName().contains("AN") && files[j].getName().endsWith(".txt")) {
						if (valorRegistros == 0) {
							if (files[j].getName().contains("US")) {
								FileReader read = new FileReader(files[j]);
								BufferedReader bRead = new BufferedReader(read);
								while (bRead.readLine() != null) {
									valorRegistros = valorRegistros + 1;
								}
								bRead.close();
								read.close();
								arrayFiles.add(files[j].getPath());
								selectFileAdjuntarArchivos();
							} else {
								continue;
							}
						} else {
							if (files[j].getName().contains("CT")) {
								FileReader read = new FileReader(files[j]);
								BufferedReader bRead = new BufferedReader(read);
								List<String> arrayLinea = new ArrayList<String>();
								String linea;
								while ((linea = bRead.readLine()) != null) {
									arrayLinea.add(linea);
								}
								FileWriter write = new FileWriter(files[j]);
								String[] arrayPalabras = new String[arrayLinea.size()];
								for (int h = 0; h < arrayLinea.size(); h++) {
									arrayPalabras = arrayLinea.get(h).split(",");
									if (!files[h].getName().contains("CT")) {
										write.write(arrayPalabras[0] + "," + arrayPalabras[1] + ","
												+ files[h].getName().replace(".txt", "") + "," + valorRegistros + "\n");
									} else {
										write.write(arrayPalabras[0] + "," + arrayPalabras[1] + ","
												+ files[h + 1].getName().replace(".txt", "") + "," + valorRegistros
												+ "\n");
									}
								}
								write.close();
								bRead.close();
								read.close();
								arrayFiles.add(files[j].getPath());
							} else {
								if (files[j].getName().contains("AF")) {
									FileReader read = new FileReader(files[j]);
									BufferedReader bRead = new BufferedReader(read);
									List<String> arrayLinea = new ArrayList<String>();
									String linea;
									while ((linea = bRead.readLine()) != null) {
										arrayLinea.add(linea);
									}
									FileWriter write = new FileWriter(files[j]);
									String[] arrayPalabras = new String[arrayLinea.size()];
									String[] codigo = new String[arrayPalabras.length];
									for (int h = 0; h < arrayLinea.size(); h++) {
										arrayPalabras = arrayLinea.get(h).split(",");
										int codigoFacturacion = 0;
										if (arrayLinea.get(h).contains("FH")) {
											if (arrayPalabras[4].length() == 9) {
												codigo = arrayPalabras[4].split("FH");
												codigoFacturacion = Integer.parseInt(codigo[1]) + 1;
											}
											String frase = "";
											for (int m = 0; m < arrayPalabras.length; m++) {
												if (m + 1 == arrayPalabras.length) {
													frase = frase + arrayPalabras[m];
													break;
												} else {
													if (m == 4) {
														frase = frase + "FH" + String.valueOf(codigoFacturacion) + ",";
													} else {
														frase = frase + arrayPalabras[m] + ",";
													}
												}
											}
											write.write(frase + "\n");
										}
									}
									write.close();
									bRead.close();
									read.close();
									arrayFiles.add(files[j].getPath());
								} else {
									if (!files[j].getName().contains("US") && !files[j].getName().contains("CT")) {
										FileReader read = new FileReader(files[j]);
										BufferedReader bRead = new BufferedReader(read);
										List<String> arrayLinea = new ArrayList<String>();
										String linea;
										while ((linea = bRead.readLine()) != null) {
											arrayLinea.add(linea);
										}
										FileWriter write = new FileWriter(files[j]);
										String[] arrayPalabras = new String[arrayLinea.size()];
										String[] codigo = new String[arrayPalabras.length];
										for (int h = 0; h < arrayLinea.size(); h++) {
											arrayPalabras = arrayLinea.get(h).split(",");
											int codigoFacturacion = 0;
											if (arrayLinea.get(h).contains("FH")) {
												if (arrayPalabras[0].length() == 9) {
													codigo = arrayPalabras[0].split("FH");
													codigoFacturacion = Integer.parseInt(codigo[1]) + 1;
												}
												String frase = "";
												for (int m = 0; m < arrayPalabras.length; m++) {
													if (m + 1 == arrayPalabras.length) {
														frase = frase + arrayPalabras[m];
														break;
													} else {
														if (m == 0) {
															frase = frase + "FH" + String.valueOf(codigoFacturacion)
																	+ ",";
														} else {
															frase = frase + arrayPalabras[m] + ",";
														}
													}
												}
												write.write(frase + "\n");
											}
										}
										write.close();
										bRead.close();
										read.close();
										arrayFiles.add(files[j].getPath());
									}
								}
							}
						}
					} else if (j == files.length) {
						break;
					}
				}
			}
		}
		for (int j = 0; j < arrayFiles.size(); j++) {
			objAux.getDriver().findElement(fileAdjuntarArchivos).sendKeys(arrayFiles.get(j));
		}
		objAux.AdminDocPdf.generaEvidencia("Se adjunta los archivos:",
				Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void clicBtnCargarArchivo() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnCargarArchivo);
		objAux.getDriver().findElement(btnCargarArchivo).click();
		objAux.AdminDocPdf.generaEvidencia("Se cargan los archivos",
				Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void clicBtnSi(boolean pPrimeraFase) throws IOException {
		if (pPrimeraFase) {
			objAux.EsperaElemento(objAux.getDriver(), btnSi);
			objAux.getDriver().findElement(btnSi).click();
		} else {
			objAux.EsperaElemento(objAux.getDriver(), btnSiConfirmarCargue);
			objAux.getDriver().findElement(btnSiConfirmarCargue).click();
		}
		objAux.AdminDocPdf.generaEvidencia("Se confirma el cargue de los archivos",
				Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void clicBtnAceptar() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnAceptar);
		objAux.getDriver().findElement(btnAceptar).click();
		objAux.AdminDocPdf.generaEvidencia("Se envía formulario", Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void getLblNumeroCuenta() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), lblNumeroCuenta);
		if (Integer.parseInt(objAux.getDriver().findElement(lblNumeroCuenta).getText()) == numeroCuenta) {
			objAux.AdminDocPdf.generaEvidencia("Se captura número de cuenta creado",
					Shutterbug.shootPage(objAux.getDriver()).highlight(objAux.getDriver().findElement(lblNumeroCuenta))
							.getImage());
		}
	}

	public void clicBtnConfirmar() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnConfirmar);
		objAux.getDriver().findElement(btnConfirmar).click();
		objAux.AdminDocPdf.generaEvidencia("Se confirma la transacción",
				Shutterbug.shootPage(objAux.getDriver()).getImage());
	}

	public void clicBtnCerrar() throws IOException {
		objAux.EsperaElemento(objAux.getDriver(), btnCerrar);
		objAux.getDriver().findElement(btnCerrar).click();
	}

	public void ingresoDatosForm(String pSedeIps, String pMes, String pAnio, String pTipoServicio,
			String pResponsablePago, String pRegional) throws Exception {
		clicOptCuenta();
		clicOptValidarRips();
		setTxtSedeIps(pSedeIps);
		clicOptSede();
		seleccionarMes(pMes);
		seleccionarAnio(pAnio);
		sendNumeroCuenta();
		setTxtValorCuenta();
		clicBtnContrato();
		clicBtnPreultimoContrato();
		selectListTipoServicio(pTipoServicio);
		selectListResponsablePago(pResponsablePago);
		selectListRegional(pRegional);
		selectFileAdjuntarArchivos();
		clicBtnCargarArchivo();
		clicBtnSi(true);
		clicBtnAceptar();
		getLblNumeroCuenta();
		clicBtnConfirmar();
		clicBtnSi(false);
		clicBtnCerrar();
	}

}