package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.VistaGrafica;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaTexto.VistaTexto;
import org.iesalandalus.programacion.utilidades.Entrada;

public class MainApp {

public static void main(String[] args) {
		
		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());//Crea un modelo con ficheros.
		//objeto de tipo FactoriaFuenteDatos Ficheros.
		
		/*Se crea interfaz grafica por defecto pero descomentando el siguiente segmento de codigo
		se abre un menú textual en el que se puede elegir entre una interfaz grafica o una de texto*/
		/*
		System.out.println("Elige un tipo de interfaz: (Grafica o Texto)");
		String entrada = Entrada.cadena();
		IVista vista = null;
		while (!entrada.equalsIgnoreCase("Grafica") && !entrada.equalsIgnoreCase("Texto")) {
			System.out.println("Elige un tipo de interfaz: (Grafica o Texto)");
			entrada = Entrada.cadena();
		}
		if (entrada.equalsIgnoreCase("Texto")) {
			 vista = new VistaTexto();
		} else if (entrada.equalsIgnoreCase("Grafica")) {
			 vista = new VistaGrafica();			
		}
		*/
		IVista vista = new VistaGrafica();
		IControlador controlador = new Controlador(modelo,vista);
		controlador.comenzar();
	}
}