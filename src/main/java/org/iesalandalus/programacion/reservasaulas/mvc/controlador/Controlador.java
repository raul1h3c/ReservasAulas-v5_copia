package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador {//Implementa interfaz

	private IVista vista; //Crea una vista y modelo utilizando la interfaz. 
	private IModelo modelo; //Cambia automaticamente al utilizar refactorizar en el IDE.
	
	public Controlador(IModelo modelo, IVista vista){//Constructor del modelo
		
		if (modelo == null || vista == null) {
			throw new NullPointerException("El modelo o la vista no pueden ser nulos");
		}
		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this);//Se utiliza para crear una referencia
	}
	
	@Override
	public void comenzar() {
		modelo.comenzar();//El modelo leera todos los objetos desde que se abre el programa hasta que se cierre
		vista.comenzar();
	}

	@Override
	public void terminar() {
		modelo.terminar();/*Todos los objetos que el modelo ha leido, ahora, al cerrar el programa 
		los va a escribir y guardar en los ficheros correspondientes.*/
		System.out.println("Saliendo del programa y guardando cambios...");
	}
	
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException { 
		modelo.insertarAula(aula);
	}
	
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.insertarProfesor(profesor);
	}
	
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException{
		modelo.borrarAula(aula);
	}
	
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.borrarProfesor(profesor);
	}
	
	@Override
	public void buscarAula(Aula aula) throws OperationNotSupportedException{
		modelo.buscarAula(aula);
	}
	
	@Override
	public void buscarProfesor(Profesor profesor) throws OperationNotSupportedException{
		modelo.buscarProfesor(profesor);
	}
	
	@Override
	public List<String> representarAulas() {//Hay que cambiar de array de string a lista de string.
		return modelo.representarAulas();	
	}
	
	@Override
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
	}
	
	@Override
	public List<String> representarReservas() {
		return modelo.representarReservas();
	}
	
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservasAula(aula);
	}
	
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}
	
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}
	
	@Override
	public boolean consultarDisponibilidadAula(Aula aula, Permanencia permanencia) {
		return modelo.consultarDisponibilidad(aula, permanencia);
	}
}