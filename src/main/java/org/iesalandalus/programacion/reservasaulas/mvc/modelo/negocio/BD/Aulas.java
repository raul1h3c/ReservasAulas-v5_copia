package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.BD;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {
	
	private static final String NOMBRE_FICHERO_AULAS = "fichero/aulas.dat"; //Fichero dat donde se guardan las aulas.
	
	private List<Aula> coleccionAulas; //Se declara la lista
	
	public Aulas() {
		coleccionAulas = new ArrayList<>(); //Se crea como arrayList
	}
	
	public Aulas(Aulas aulas) {
		setAulas(aulas);
	}
	
	@Override
	public void comenzar() {
		leer();
	}
	
	private void leer() {
		File aulas = new File(NOMBRE_FICHERO_AULAS);
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(aulas))){
			//ObjectOutputStream FileInputStream para entrada de objetos
			Aula aula = null;
			
			do {
				
				aula = (Aula) in.readObject();//Para que lea el tipo de objeto hay que castear Aula
				
				try {
					insertar(aula);
				} catch (OperationNotSupportedException e) {
					e.printStackTrace();
				}
				
			} while(aula != null);
			
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
		File aulas = new File(NOMBRE_FICHERO_AULAS);
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(aulas))){
			//ObjectOutputStream FileOutputStream para salida
			for (Aula aula : coleccionAulas) {
				out.writeObject(aula);
			}//Se recorre la lista y se escriben todos los objetos de esta
			out.close();
			
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		
	}
	
	private void setAulas(Aulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		coleccionAulas = copiaProfundaAulas(aulas.coleccionAulas);
	}
	
	@Override
	public List<Aula> getAulas() {
		
		List<Aula> aulasOrdenadas = copiaProfundaAulas(coleccionAulas);
		aulasOrdenadas.sort(Comparator.comparing(Aula::getNombre)); //Compara por nombre de aulas alfabeticamente.
		return aulasOrdenadas;
	}
	
	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		
		List<Aula> copiaAulas = new ArrayList<>();
		Iterator<Aula> I = aulas.iterator();//Se crea el iterator, o se puede crear dentro del for.
		
		for(aulas.iterator(); I.hasNext();) {//Itera mientras el siguiente índice de I no esté vacio.
			Aula aula = I.next();//Por cada iteracion se le da un valor distinto a aula
			copiaAulas.add(new Aula(aula));//Ese valor se añade a la lista copiaAulas
		}
		return copiaAulas;
	}
	
	@Override
	public int getNumAulas() {
		
		return coleccionAulas.size();//Como array.length
	}
	
	@Override
	public void insertar (Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		if (coleccionAulas.contains(aula)) {//Si ya existe el aula lanza excepcion.
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		} else { //Si no existe la inserta
			coleccionAulas.add(new Aula(aula));
		}
	}
	
	@Override
	public Aula buscar (Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		if (coleccionAulas.contains(aula)) { //Si encuentra el aula la devuelve
			System.out.println("Se ha encontrado el aula en el índice " + coleccionAulas.indexOf(aula) + "  ");
			return new Aula(aula);
		}else {
			System.out.println("No se ha encontrado el aula.");
			return null;
		}
	}
	
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		if (coleccionAulas.contains(aula)) {
			coleccionAulas.remove(aula);//Si existe la borra, si no, lanza excepcion.
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		}
	}

	@Override
	public List<String> representar() {
		
		List<String> representar = new ArrayList<>();//Lista de string 
		Iterator<Aula> I = getAulas().iterator();//Iterator que recorre las aulas existentes llamando a getAulas
		
		for (getAulas().iterator(); I.hasNext();) {
			
			representar.add(I.next().toString());/*Por cada aula encontrada se le pasan los valores
												   a representar, pasandolos primero a string*/
		}
		return representar;	
	}
}