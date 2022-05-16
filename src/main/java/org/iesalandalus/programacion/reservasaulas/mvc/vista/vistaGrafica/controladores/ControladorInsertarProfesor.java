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

public class ControladorInsertarProfesor {
	
	@FXML private TextField TFNombre, TFCorreo, TFTelefono;
	@FXML private Button BTInsertar;
	
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
	private void insertarProfesor() {
		Profesor profesorInsertar = null;
		try {
			profesorInsertar = getProfesor();
			controladorPrincipal.insertarProfesor(profesorInsertar);
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	private Profesor getProfesor() {
		Profesor profesor = null;
		String nombre = null;
		String correo = null;
		String telefono = null;

		if (TFNombre.getText() == null || TFNombre.getText().isBlank()) {
		} else {
			 nombre = TFNombre.getText();			
		}
		if (TFCorreo.getText() == null || TFCorreo.getText().isBlank()) {
		} else {
			 correo = TFCorreo.getText();			
		}
			telefono = TFTelefono.getText();
			
		if (telefono == null) {
			profesor = new Profesor(nombre,correo);
		} else if (!(telefono == null)) {
			profesor = new Profesor(nombre,correo,telefono);
		}
		
		
		return profesor;
	}

}
