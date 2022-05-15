package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.VistaGrafica;

public class MainApp {

public static void main(String[] args) {
		
		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());//Crea un modelo con ficheros.
		//objeto de tipo FactoriaFuenteDatos Ficheros.
		IVista vista = new VistaGrafica();
		IControlador controlador = new Controlador(modelo,vista);
		controlador.comenzar();
		
	}
}
