package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.resources.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnularReserva {
		
	@FXML private TextField TFCorreo, TFNombre;
	@FXML private DatePicker DPPermanencia;
	@FXML private Button BTAnular;
	
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
	private void anularReserva() {
		Reserva reserva = getReserva();
		try {
			controladorPrincipal.anularReserva(reserva);
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	private String getCorreo() {
		String correo = TFCorreo.getText();
		return correo;
	}
	
	private String getNombre() {
		String nombre = TFNombre.getText();
		return nombre;
	}
	
	
	private Reserva getReserva() {
		Aula aula = Aula.getAulaFicticia(getNombre());
		Profesor profesor = Profesor.getProfesorFicticio(getCorreo());
		Permanencia permanencia = null;
		
		Reserva reserva = new Reserva(profesor, aula, permanencia);
		
		return reserva;
	}
	

}