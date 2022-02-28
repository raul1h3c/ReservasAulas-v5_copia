package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Reservas {
	
	private List<Reserva> coleccionReservas;
	
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}
	
	public Reservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		setReservas(reservas);
	}
	
	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		coleccionReservas = copiaProfundaReservas(reservas.coleccionReservas);
	}
	
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		
		List<Reserva> copiaReservas = new ArrayList<>();
		Iterator<Reserva> I = reservas.iterator();
		
		for(reservas.iterator(); I.hasNext();) {
			Reserva reserva = I.next();
			copiaReservas.add(new Reserva(reserva));
		}
		return reservas;
	}
	
	public List<Reserva> getReservas(){
		return copiaProfundaReservas(coleccionReservas);
	}
	
	public int getNumReservas() {
		return coleccionReservas.size();
	}
	
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede realizar una reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
		} else {
			coleccionReservas.add(new Reserva(reserva));
		}
	}
	
	public Reserva buscar(Reserva reserva) {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar un reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			System.out.println("Se ha encontrado " + reserva + "en el índice " + coleccionReservas.indexOf(reserva) + "  ");
			return new Reserva(reserva);
		} else {
			System.out.println("No se ha encontrado la reserva.");
			return null;
		}
	}
	
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			coleccionReservas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		}		
	}
	
	public List<String> representar() {
		
		List<String> representar = new ArrayList<>();
		Iterator<Reserva> I = getReservas().iterator();		
		
		for(getReservas().iterator(); I.hasNext();) {
			representar.add(I.next().toString());
		}
		return representar;
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> I = getReservas().iterator();
		
		for (getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();//Le va dando el valor del iterator a reserva
			
			if(reserva.getProfesor().equals(profesor)) {//Si el profesor coincide, añade una reserva a la lista que luego mostrará
				reservas.add(new Reserva(reserva));
			}
		}
		return reservas;
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> I = getReservas().iterator();
		
		for (getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();
			
			if(reserva.getAula().equals(aula)) {
				reservas.add(new Reserva(reserva));
			}
		}
		return reservas;
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
	
		List<Reserva> reservas = new ArrayList<>();
		Iterator<Reserva> I = getReservas().iterator();
		
		for (getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();
			
			if(reserva.getPermanencia().equals(permanencia)) {
				reservas.add(new Reserva(reserva));
			}
		}
		return reservas;
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		
		boolean disponible = true;
		
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
			for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
				
				Reserva reserva = I.next();//Recorre la lista con iterator y le pasa valores a reserva.
				if (reserva.getAula().equals(aula)&& reserva.getPermanencia().equals(permanencia)) {
					disponible = false;
				}  //Si el aula y la permanencia introducidas coinciden con con las existentes en la lista, devuelve falso.
		}
		return disponible;
	}
}