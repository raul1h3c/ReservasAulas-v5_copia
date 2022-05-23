package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.BD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	//Estas string son los nombres que los atributos de los objetos van a recibir en MongoDB
	
	//Aulas
	public static final String NOMBRE = "nombre";
	public static final String PUESTOS = "Nº puestos";
	
	//Profesores
	public static final String NOMBRE_P = "Nombre";
	public static final String CORREO = "Correo";
	public static final String TELEFONO = "Nº telefono";
	
	//Reservas
	public static final String AULA = "aula";
	public static final String PROFESOR = "Profesor";

	//Permanencia
	public static final String HORA = "Hora";
	public static final String TRAMO = "Tramo";
	public static final String FECHA = "Fecha";
	public static final String PERMANENCIA = "Permanencia";
	public static final String PERMANENCIA_TRAMO = PERMANENCIA + "." + TRAMO;
	public static final String PERMANENCIA_HORA = PERMANENCIA + "." + HORA;
	public static final String PERMANENCIA_DIA = PERMANENCIA + "." + FECHA;
	public static final String TIPO = "Tipo";
	
	public static final String PROFESOR_CORREO = PROFESOR + "." + CORREO;
	public static final String AULA_NOMBRE = AULA + "." +  NOMBRE;
	public static final String PERMANENCIA_TIPO = PERMANENCIA + "." + TIPO;
	public static final String TIPO_HORA = "Hora";
	public static final String TIPO_TRAMO = "Tramo";
	
	//Se cambia el formato para que mongodb lea
	public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	//Datos de la conexion especificados en la tarea.
	public static final String BD = "reservasaulas";
	public static final String SERVER = "localhost";
	public static final Integer PUERTO = 27017;
	public static final String USUARIO = "reservasaulas";
	public static final String CONTRASEÑA = "reservasaulas-2022";
	
	//Se crea el objeto MongoClient aqui como static para poder usarlo en otros metodos
	private static MongoClient conexion;

	public MongoDB() {
		
	}

	private static MongoClient crearConexion() {
		if (conexion == null) {
			MongoCredential datos = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASEÑA.toCharArray());
			conexion = MongoClients.create(MongoClientSettings.builder().applyToClusterSettings(builder -> 
			builder.hosts(Arrays.asList(new ServerAddress(SERVER, PUERTO)))).credential(datos).build());
		}
		return conexion;
	}
	
	public static MongoDatabase getBD() {
		if (conexion == null) {
			crearConexion();
		}
		return conexion.getDatabase(BD);
	}//Se devuelve la BD 

	public static void cerrarConexion() {
		if (conexion != null) {
			conexion.close();
		}//Si la conexion no esta cerrada (nula) la cierra
	}

	public static Document getDocumentoAula(Aula aula) {
		if (aula != null) {
			
			String nombre = aula.getNombre();
			int nPuestos = aula.getPuestos();
			//Se obtienen los atributos del aula
			return new Document().append(NOMBRE, nombre).append(PUESTOS, nPuestos);
			//Se utilizan los atributos del aula para crear un documento de esta, se utiliza append para 
			//darle el nombre que ese atributo tendra en MongoDB: (Nombre: ejemplo) (Puestos: 1234)
			
		} else {			
				System.out.println("No se ha introducido ningun aula");
				return null;
		}
	}
	
	public static Document getDocumentoProfesor(Profesor profesor) {
		if (profesor != null) {
			
			//Se obtienen los atributos del profesor
			String nombre = profesor.getNombre();
			String correo = profesor.getCorreo();
			String telefono = profesor.getTelefono();
			
			return new Document().append(NOMBRE_P, nombre).append(CORREO, correo).append(TELEFONO, telefono);
			//Se utilizan los atributos del profesor para crear un documento de este, se utiliza append para 
			//darle el nombre que ese atributo tendra en MongoDB: (Nombre: ejemplo) (Correo: ejemplo)
		
		} else {			
			System.out.println("No se ha introducido ningun profesor");
			return null;
		}
	}
	
	public static Aula getAula(Document document) {
		if (document != null) {
			return new Aula(document.getString(NOMBRE), document.getInteger(PUESTOS));
			//Se devuelve un objeto aula a partir del documento.
		}
		System.out.println("El aula no puede ser nula");
		return null;
	}
	
	public static Profesor getProfesor(Document document) {
		if (document != null) {
			return new Profesor(document.getString(NOMBRE_P), document.getString(CORREO), document.getString(TELEFONO));

		} else {
			System.out.println("El profesor no puede ser nulo");
			return null;
		}
	}
	
	//El criterio de ordenacion simplemente ordena el documento por el atributo seleccionado utilizando el sort de
	//la clase aulas
	public static Document getCriterioOrdenacionAula() {
		return new Document().append(NOMBRE, 1);
	}
	
	//El criterio de ordenacion simplemente ordena el documento por el atributo seleccionado utilizando el sort de
	//la clase profesores
	public static Document getCriterioOrdenacionProfesor() {
		return new Document().append(NOMBRE_P, 1);
	}

	public static Document getDocumentoReserva(Reserva reserva) {
		if (reserva == null) {
			return null;
		}
		
		//Atributos de reserva
		Aula aula = reserva.getAula();
		Profesor profesor = reserva.getProfesor();
		Permanencia permanencia = reserva.getPermanencia();
		
		String fecha = permanencia.getDia().format(FORMATO_DIA);
		
		//Crea documentos utilizando los metodos a partir de los atributos de la reserva
		Document aula2 = getDocumentoAula(aula);
		Document profesor2 = getDocumentoProfesor(profesor);
		Document fecha2 = new Document().append(FECHA, fecha);
		
		if (permanencia instanceof PermanenciaPorTramo) {
			String tramo = ((PermanenciaPorTramo) permanencia).getTramo().name();//.name() no .toString()
			fecha2.append(TIPO, TIPO_TRAMO).append(TRAMO, tramo);//A la fecha le añade tipo tramo y tramo
		} else {
			String hora = ((PermanenciaPorHora) permanencia).getHora().format(FORMATO_HORA);
			fecha2.append(TIPO, TIPO_HORA).append(HORA, hora);//A la fecha le añade tipo tramo y tramo
		}
		
		//Devuelve un nuevo documento de reserva utilizando las constantes y los otros documentos.
		return new Document().append(PROFESOR, profesor2).append(AULA, aula2).append(PERMANENCIA, fecha2);
	}

	public static Reserva getReserva(Document document) {
		
		if (document != null) {
			
			//Se crea un aula a partir del documento reserva, hay que castear como Document
			Aula aula = new Aula(getAula((Document) document.get(AULA)));
			//Se crea un profesor a partir del documento reserva, hay que castear como Document
			Profesor profesor = new Profesor(getProfesor((Document) document.get(PROFESOR)));
			//Crear documento permanencia
			
			Document permanencia = (Document) document.get(PERMANENCIA);
			LocalDate fecha = LocalDate.parse(permanencia.getString(FECHA), FORMATO_DIA);
			
			String tipo = permanencia.getString(TIPO);
			Permanencia permanencia2 = null;
			//Se crea la permanencia2 antes para poder devolverla
			if (tipo.equals(TIPO_TRAMO)) {
				Tramo tramo = Tramo.valueOf(permanencia.getString(TRAMO));
				permanencia2 = new PermanenciaPorTramo(fecha, tramo);
			} else {
				LocalTime hora = LocalTime.parse(permanencia.getString(HORA), FORMATO_HORA);
				permanencia2 = new PermanenciaPorHora(fecha, hora);
			}//Dependiendo del tipo de permanencia que sea crea un objeto reserva con ese tipo
			return new Reserva(profesor, aula, permanencia2);
			}
			   else {
			return null;	
		}
	}
}