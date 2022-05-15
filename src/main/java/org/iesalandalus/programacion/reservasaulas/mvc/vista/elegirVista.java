package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaGrafica.VistaGrafica;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaTexto.VistaTexto;

public enum elegirVista {
//Igual que FactoriaFuenteDatos
	
	vistaTexto {
		
		public IVista crear() {
			return new VistaTexto();
		}
	},
	
	vistaGrafica {
		public IVista crear() {
			return new VistaGrafica();
		}
	};
	
	public abstract IVista crear();
}