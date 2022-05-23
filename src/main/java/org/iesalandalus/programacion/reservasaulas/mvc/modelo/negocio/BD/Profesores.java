package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.BD;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

import com.mongodb.client.MongoCollection;

public class Profesores implements IProfesores {
	
	private static final String COLECCION = "profesores";

	private MongoCollection<Document> coleccionProfesores;
	
	public Profesores() {
		
	}
	
	@Override
	public void comenzar() {
		coleccionProfesores = MongoDB.getBD().getCollection(COLECCION);
	}//getCollection se refiere a una coleccion en MongoDB, si la coleccion no existe, la crea.
	
	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}
	
	@Override
	public List<Profesor> getProfesores() {
		List<Profesor> profesores = new ArrayList<>();
		for (Document documentoProfesor : coleccionProfesores.find().sort(MongoDB.getCriterioOrdenacionProfesor())) {
			Profesor profesor=MongoDB.getProfesor(documentoProfesor);
			profesores.add(profesor);
		}
		return profesores;
	}
	
	@Override
	public int getNumProfesores() {
		return (int)coleccionProfesores.countDocuments();
	}
	
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede insertar un profesor nulo.");
		}//No utilizo buscar para que no aparezca el mensaje de buscar.
		Document documentoProfesor = coleccionProfesores.find().filter(eq(MongoDB.CORREO, profesor.getCorreo())).first();
		if (documentoProfesor != null) {
			throw new OperationNotSupportedException("El aula ya existe.");
		} else {
			coleccionProfesores.insertOne(MongoDB.getDocumentoProfesor(profesor));
		}
	}
	
	@Override
	public Profesor buscar(Profesor profesor) {
		Document documentoProfesor = coleccionProfesores.find().filter(eq(MongoDB.CORREO, profesor.getCorreo())).first();
		if (documentoProfesor != null) {
			System.out.println("Se ha encontrado el profesor.");
		} else {
			System.out.println("No se ha encontrado el profesor.");
		}
		return MongoDB.getProfesor(documentoProfesor);
	}
	
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}//No utilizo buscar para que no aparezca el mensaje de buscar.
		Document documentoProfesor = coleccionProfesores.find().filter(eq(MongoDB.CORREO, profesor.getCorreo())).first();
		if (documentoProfesor != null) {
			coleccionProfesores.deleteOne(eq(MongoDB.CORREO, profesor.getCorreo()));
		} else {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
		} 
	}

	@Override
	public List<String> representar() {//Igual que en aulas
		
		List<String> representar = new ArrayList<>();
		Iterator<Profesor> I = getProfesores().iterator();
		
		for (getProfesores().iterator(); I.hasNext();) {
			
			representar.add(I.next().toString());
		}
		return representar;	
	}
}