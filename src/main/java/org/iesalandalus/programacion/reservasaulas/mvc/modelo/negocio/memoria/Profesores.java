package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

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
	
	@Override
	public List<Profesor> getProfesores() {
		
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores(coleccionProfesores);
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getCorreo));//Devuelve profesores ordenados por correo
		
		return profesoresOrdenados;
	}
	
	@Override
	public int getNumProfesores() {
		
		return coleccionProfesores.size();
	}
	
	@Override
	public void insertar (Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		} else {
			coleccionProfesores.add(new Profesor(profesor));
		}
	}
	
	@Override
	public Profesor buscar (Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			System.out.println("Se ha encontrado el profesor en el índice " + coleccionProfesores.indexOf(profesor) + "  ");
			return new Profesor(profesor);
		}else {
			System.out.println("No se ha encontrado el profesor.");
			return null;
		}
	}
	
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			coleccionProfesores.remove(profesor);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		}
	}

	@Override
	public List<String> representar() {
		
		List<String> representar = new ArrayList<>();
		Iterator<Profesor> I = getProfesores().iterator();
		
		for (getProfesores().iterator(); I.hasNext();) {
			
			representar.add(I.next().toString());
		}
		return representar;	
	}
}