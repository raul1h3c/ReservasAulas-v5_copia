package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.resources.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorEliminarProfesor {
	
	@FXML private TextField TFCorreo;
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
	private void eliminarProfesor() {
		String correo = getCorreo();
		Profesor profesor = Profesor.getProfesorFicticio(correo);
		
		try {
			controladorPrincipal.borrarProfesor(profesor);
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
	}
	private String getCorreo() {
		String correo = null;
		if (TFCorreo.getText() == null || TFCorreo.getText().isBlank()) {
			Dialogos.mostrarDialogoError("ERROR", "Error: No se ha introducido ningún correo.");
		} else {
			 correo = TFCorreo.getText();			
		}
		return correo;
	}

}
