package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public class Profesores {

	private List<Profesor> coleccionProfesores;
	
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}
	
	public Profesores(Profesores profesores) {
		setProfesores(profesores);
	}
	
	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		coleccionProfesores = copiaProfundaProfesores(profesores.coleccionProfesores);
	}
	
	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		
		List<Profesor> copiaProfesores = new ArrayList<>();
		Iterator<Profesor> I = profesores.iterator();
		
		for(profesores.iterator(); I.hasNext();) {
			Profesor profesor = I.next();
			copiaProfesores.add(new Profesor(profesor));
		}
		return copiaProfesores;
	}
	
	public List<Profesor> getProfesores() {
		
		return copiaProfundaProfesores(coleccionProfesores);
	}
	
	public int getNumProfesores() {
		
		return coleccionProfesores.size();
	}
	
	public void insertar (Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
		} else {
			coleccionProfesores.add(new Profesor(profesor));
		}
	}
	
	public Profesor buscar (Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			System.out.println("Se ha encontrado el "  + profesor + " en el índice " + coleccionProfesores.indexOf(profesor) + "  ");
			return new Profesor(profesor);
		}else {
			System.out.println("No se ha encontrado el profesor.");
			return null;
		}
	}
	
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			coleccionProfesores.remove(profesor);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
		}
	}

	public List<String> representar() {
		
		List<String> representar = new ArrayList<>();
		Iterator<Profesor> I = getProfesores().iterator();
		
		for (getProfesores().iterator(); I.hasNext();) {
			
			representar.add(I.next().toString());
		}
		return representar;	
	}
}