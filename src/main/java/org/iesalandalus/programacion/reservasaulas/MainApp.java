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
	
		/*Se crea un modelo de base de datos por defecto pero descomentando el siguiente segmento de codigo
		se abre un menú textual en el que se puede elegir entre un modelo de base de datos o uno de ficheros*/
	
		/*System.out.println("Elige un tipo de almacenamiento: (Ficheros o Base de datos)");
		String entradaModelo = Entrada.cadena();
		IModelo modelo = null;
		
		while (!entradaModelo.equalsIgnoreCase("Ficheros") && !entradaModelo.equalsIgnoreCase("Base de datos")) {
			System.out.println("Elige un tipo de almacenamiento: (Ficheros o Base de datos)");
			entradaModelo = Entrada.cadena();
		}
		
		if (entradaModelo.equalsIgnoreCase("Ficheros")) {
			modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		} else if (entradaModelo.equalsIgnoreCase("Base de datos")) {
			modelo = new Modelo(FactoriaFuenteDatos.BD.crear());
		}*/
		
		/*Se crea interfaz de texto por defecto pero descomentando el siguiente segmento de codigo
		se abre un menú textual en el que se puede elegir entre una interfaz grafica o una de texto*/
		
		/*System.out.println("Elige un tipo de interfaz: (Grafica o Texto)");
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
		}*/
		
		IModelo modelo = new Modelo(FactoriaFuenteDatos.BD.crear());//Crea un modelo con BD.
		IVista vista = new VistaTexto();//Crea una vista de texto por defecto.
		IControlador controlador = new Controlador(modelo,vista);
		controlador.comenzar();
	}
}