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

	
	@FXML private Button BTSalirPrincipal,BTInsertarAula,BTEliminarAula,BTListarAula,BTConsultarDisponibilidad;
	@FXML private MenuItem MICerrar;
	
	private Stage insertarAulaStage, eliminarAulaStage, listarAulaStage, consultarDisponibilidadStage;
	private ControladorInsertarAula controllerInsertarAula;
	private ControladorEliminarAula controllerEliminarAula;
	private ControladorListarAula controllerListarAula;
	private ControladorConsultarDisponibilidad controllerConsultarDisponibilidad;
	
	
	private IControlador controladorPrincipal;
	public void setControladorPrincipal(IControlador controlador) {
		this.controladorPrincipal = controlador;
	}
	
	
	@FXML
	public void abrirInsertarAula() throws IOException {
		crearVentanaInsertarAula();
		insertarAulaStage.showAndWait();
	}
	
	@FXML 
	public void abrirEliminarAula() throws IOException {
		crearVentanaEliminarAula();
		eliminarAulaStage.showAndWait();
	}
	
	@FXML
	private void salir(ActionEvent evento) {
		((Stage) BTSalirPrincipal.getParent().getScene().getWindow()).close();
	}
	
	private void crearVentanaInsertarAula() throws IOException {
			insertarAulaStage = new Stage();
			FXMLLoader loader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/InsertarAula.fxml"));
			AnchorPane raiz = loader.load();
			controllerInsertarAula = loader.getController();
			controllerInsertarAula.setControladorPrincipal(controladorPrincipal);
			
			Scene scene = new Scene(raiz,500,400);
			insertarAulaStage.setTitle("Insertar aulas");
			insertarAulaStage.initModality(Modality.APPLICATION_MODAL);
			insertarAulaStage.setScene(scene);
		} 
	
	private void crearVentanaEliminarAula() throws IOException {
		if (eliminarAulaStage == null) {
			eliminarAulaStage = new Stage();
			FXMLLoader loader2 = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/EliminarAula.fxml"));
			AnchorPane raiz2 = loader2.load();
			controllerEliminarAula = loader2.getController();
			controllerEliminarAula.setControladorPrincipal(controladorPrincipal);
			
			Scene scene2 = new Scene(raiz2,500,400);
			insertarAulaStage.setTitle("Eliminar aulas");
			insertarAulaStage.initModality(Modality.APPLICATION_MODAL);
			insertarAulaStage.setScene(scene2);
		} else {
		}
	}
	
}