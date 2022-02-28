package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;

public class Controlador {

	private Vista vista;
	private Modelo modelo;
	
	public Controlador(Modelo modelo, Vista vista){//Constructor del modelo
		
		if (modelo == null || vista == null) {
			throw new NullPointerException("El modelo o la vista no pueden ser nulos");
		}
		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this);//Se utiliza para crear una referencia
	}
	
	public void comenzar() {
		vista.comenzar();
	}

	public void terminar() {
		System.out.println("Saliendo del programa...");
	}
	
	public void insertarAula(Aula aula) throws OperationNotSupportedException { 
		modelo.insertarAula(aula);
	}
	
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.insertarProfesor(profesor);
	}
	
	public void borrarAula(Aula aula) throws OperationNotSupportedException{
		modelo.borrarAula(aula);
	}
	
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.borrarProfesor(profesor);
	}
	
	public void buscarAula(Aula aula) throws OperationNotSupportedException{
		modelo.buscarAula(aula);
	}
	
	public void buscarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.buscarProfesor(profesor);
	}
	
	public List<String> representarAulas() {//Hay que cambiar de array de string a lista de string.
		return modelo.representarAulas();	
	}
	
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
	}
	
	public List<String> representarReservas() {
		return modelo.representarReservas();
	}
	
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservasAula(aula);
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}
	
	public boolean consultarDisponibilidadAula(Aula aula, Permanencia permanencia) {
		return modelo.consultarDisponibilidad(aula, permanencia);
	}
}