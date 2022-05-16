package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import java.io.IOException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.resources.LocalizadorRecursos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPrincipal {

	
	@FXML private Button BTSalirPrincipal,BTInsertarAula;
	@FXML private MenuItem MICerrar;
	
	private Stage insertarAulaStage;
	private ControladorInsertarAula controllerInsertarAula;
	
	
	private IControlador controladorPrincipal;
	public void setControladorPrincipal(IControlador controlador) {
		this.controladorPrincipal = controlador;
	}
	
	
	
	private void crearVentanaInsertarAula() throws IOException {
		if (insertarAulaStage == null) {
			insertarAulaStage = new Stage();
			FXMLLoader loader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/InsertarAula.fxml"));
			AnchorPane raiz = loader.load();
			controllerInsertarAula = loader.getController();
			controllerInsertarAula.setControladorPrincipal(controladorPrincipal);
			
			Scene insertarAulas = new Scene(raiz);
			insertarAulaStage.setTitle("Insertar aulas");
			insertarAulaStage.initModality(Modality.APPLICATION_MODAL);
			insertarAulaStage.setScene(insertarAulas);
		} else {
		}
	}
	
	@FXML
	public void abrirInsertarAula() throws IOException {
		crearVentanaInsertarAula();
		insertarAulaStage.showAndWait();
		
	}
	
	@FXML
	private void salir(ActionEvent evento) {
		((Stage) BTSalirPrincipal.getParent().getScene().getWindow()).close();
	}
}