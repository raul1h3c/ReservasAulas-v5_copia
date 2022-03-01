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
		//Hay que crear una permanencia pero como la clase Permanencia es abstracta no se puede instanciar, asi que se inicializa como nula.
		Permanencia permanenciaC = null;
		//Se utiliza instanceof para ver si el supertipo permanencia es de subtipo por hora o por tramo.
		//Se devuelve una permanencia del subtipo que encuentra.
		if (permanencia instanceof PermanenciaPorTramo) {
			permanenciaC = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia); //Hay que castear la permanencia en el subtipo.
		}
		if (permanencia instanceof PermanenciaPorHora) {
			permanenciaC = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		}
		return permanenciaC;
	}
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		//Se utiliza instanceof para ver si el supertipo permanencia es de subtipo por hora o por tramo.
		if (permanencia instanceof PermanenciaPorTramo) {
			this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
		if (permanencia instanceof PermanenciaPorHora) {
			this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		}
	}
	public Aula getAula() {
		return aula;
	}
	private void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula = new Aula(aula); //Para evitar aliasing hay que devolver una copia del objeto.
	}
	public Profesor getProfesor() {
		return profesor;
	}
	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = new Profesor(profesor);
	}
	
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		
		Profesor profesor = new Profesor("Profesor", "de@ejempl.o", "999999999");
		return new Reserva(profesor, aula, permanencia);
	}
	
	public  float getPuntos() {
		float puntos = permanencia.getPuntos() + aula.getPuntos();
	
		return puntos;
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