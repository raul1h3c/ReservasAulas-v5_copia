package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

public class Reserva {
	
	private Permanencia permanencia;
	private Aula aula;
	private Profesor profesor;
	
	public Reserva (Profesor profesor, Aula aula, Permanencia permanencia) {
		
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	public Reserva (Reserva reservaC) {
		
		if(reservaC == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		
		setProfesor(reservaC.getProfesor());
		setAula(reservaC.getAula());
		setPermanencia(reservaC.getPermanencia());	
	}
	public Permanencia getPermanencia() {
		return permanencia;
	}
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		this.permanencia = permanencia;
	}
	public Aula getAula() {
		return aula;
	}
	private void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula = aula;
	}
	public Profesor getProfesor() {
		return profesor;
	}
	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = profesor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}
	@Override
	public String toString() {
		return String.format("Reserva: %s,  %s,  %s]", permanencia, aula, profesor);
	}
}