package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class FactoriaFuenteDatosMemoria implements IFuenteDatos {
	
	//Esta clase crea las clases Aulas Profesores y reservas
	
	public IAulas crearAulas() {
		
		return new Aulas();
	}
	
	public IProfesores crearProfesores() {
		
		return new Profesores();
	}
	
	public IReservas crearReservas() {
		
		return new Reservas();
	}
} 