package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class Permanencia {//Clase abstracta por lo que no se puede instanciar.
	
	private LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Permanencia(LocalDate dia) {
		
		setDia(dia);
	}
	
	public Permanencia(Permanencia permanenciaC) {
		if (permanenciaC == null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		}
		setDia(permanenciaC.getDia());
	}
	
	public LocalDate getDia() {
		return dia;
	}
	private void setDia(LocalDate dia) {
		
		if (dia == null) {
			throw new NullPointerException("ERROR: El día de una permanencia no puede ser nulo.");
		}
		this.dia = dia;
	}
	
	//Metodos abstractos que van a heredar las otras dos clases.
	public abstract int getPuntos();
	public abstract int hashCode();
	public abstract boolean equals(Object obj);

	@Override
	public String toString() {
		return String.format("Permanencia: [Dia = %s]", dia.format(FORMATO_DIA));
	}//Se utiliza format en el toString para que devuelva la fecha con el formato adecuado.
}