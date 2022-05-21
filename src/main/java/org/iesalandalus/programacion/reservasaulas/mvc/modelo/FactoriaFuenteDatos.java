package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.BD.FactoriaFuenteDatosBD;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

public enum FactoriaFuenteDatos {

	MEMORIA {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}//Crea un nuevo objeto FactoriaFuenteDatosMemoria.
	},
	
	FICHEROS {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	},
	
	BD {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosBD();
		}
	};
	
	public abstract IFuenteDatos crear();
}