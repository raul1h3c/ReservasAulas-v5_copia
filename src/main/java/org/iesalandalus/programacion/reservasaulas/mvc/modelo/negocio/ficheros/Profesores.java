package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	
	private static final String NOMBRE_FICHERO_PROFESORES = "fichero/profesores.dat";

	private List<Profesor> coleccionProfesores;
	
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}
	
	public Profesores(Profesores profesores) {
		
		setProfesores(profesores);
	}
	
	@Override
	public void comenzar() {
		leer();
	}
	
	private void leer() {
		File profesores = new File(NOMBRE_FICHERO_PROFESORES);
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(profesores))){
			//ObjectOutputStream FileInputStream para entrada de objetos
			Profesor profesor = null;
			
			do {
				
				profesor = (Profesor) in.readObject();//Para que lea el tipo de objeto hay que castear
				
				try {
					insertar(profesor);
				} catch (OperationNotSupportedException e) {
					e.printStackTrace();
				}
				
			} while(profesor != null);
			
			in.close();//Se cierra el flujo
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());	
		} catch (EOFException e) {
			System.out.println(e.getMessage());	
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Override
	public void terminar() {
		escribir();
	}
	
	private void escribir() {
		File profesores = new File(NOMBRE_FICHERO_PROFESORES);
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(profesores))){
			//ObjectOutputStream FileOutputStream para salida
			for (Profesor profesor : coleccionProfesores) {
				out.writeObject(profesor);
			}//Se recorre la lista y se escriben todos los objetos de esta
			out.close();
			
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		
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