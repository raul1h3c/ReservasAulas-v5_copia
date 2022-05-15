package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ControladorPrincipal {

	private IControlador controladorPrincipal;
	
	@FXML private Button BTSalirPrincipal;
	@FXML private MenuItem MICerrar;
	
	public void setControladorPrincipal(IControlador controlador) {
		this.controladorPrincipal = controlador;
	}
	
	@FXML
	public void salir(ActionEvent evento) {
		((Stage) BTSalirPrincipal.getParent().getScene().getWindow()).close();
	}
}