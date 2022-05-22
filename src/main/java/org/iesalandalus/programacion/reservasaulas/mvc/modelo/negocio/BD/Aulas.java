package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.BD;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

import com.mongodb.client.MongoCollection;

public class Aulas implements IAulas {
	
	private static final String COLECCION = "aulas";
	
	private MongoCollection<Document> coleccionAulas;
	
	public Aulas()
	{
		
	}
	
	@Override
	public void comenzar() {
		coleccionAulas = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}
	
	@Override
	public List<Aula> getAulas() {
		List<Aula> aulas = new ArrayList<>();
		for (Document documentoAula : coleccionAulas.find().sort(MongoDB.getCriterioOrdenacionAula())) {
			Aula aula=MongoDB.getAula(documentoAula);
			aulas.add(aula);
		}
		return aulas;
	}
	
	@Override
	public int getNumAulas() {
		return (int)coleccionAulas.countDocuments();
	}
	
	@Override
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		}
		if (buscar(aula) != null) {
			throw new OperationNotSupportedException("El aula ya existe.");
		} else {
			coleccionAulas.insertOne(MongoDB.getDocumento(aula));
		}
	}
	
	@Override
	public Aula buscar(Aula aula) {
		Document documentoAula = coleccionAulas.find().filter(eq(MongoDB.NOMBRE, aula.getNombre())).first();
		return MongoDB.getAula(documentoAula);
	}
	
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		if (buscar(aula) != null) {
			coleccionAulas.deleteOne(eq(MongoDB.NOMBRE, aula.getNombre()));
		} else {
			throw new OperationNotSupportedException("El aula a borrar no existe.");
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