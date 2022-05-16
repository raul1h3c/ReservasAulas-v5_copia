package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.resources.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorEliminarAula {
		
	@FXML private TextField TFNombre;
	@FXML private Button BTEliminar;
	
	private IControlador controladorPrincipal;
	
	public void setControladorPrincipal(IControlador controlador) {
		this.controladorPrincipal = controlador;
	}
	
	@FXML private Button BTSalir;
	@FXML
	private void salir(ActionEvent evento) {
		((Stage) BTSalir.getParent().getScene().getWindow()).close();
	}
	
	@FXML
	private void eliminarAula() {
		
		String nombre = getNombre();
		Aula aulaBorrar = Aula.getAulaFicticia(nombre);
		try {
			controladorPrincipal.borrarAula(aulaBorrar);
			Dialogos.mostrarDialogoInformacion(nombre, "Se ha eliminado el aula.");
		} catch (OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError(nombre, "Se ha producido un error.");
			e.printStackTrace();
		}
	}
	private String getNombre() {
		String nombre = null;
		if (TFNombre.getText() == null || TFNombre.getText().isBlank()) {
			Dialogos.mostrarDialogoError("ERROR", "Error: No se ha introducido ningún nombre.");
		} else {
			 nombre = TFNombre.getText();			
		}
		return nombre;
	}
}