package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Vista {
	
	private static final String ERROR = "Ha habido un error";
	private static final String NOMBRE_VALIDO = "";
	private static final String CORREO_VALIDO = "[a-zñÑA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?(?:\\.[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?)";
	private Controlador controlador;
	
	public Vista() {
		Opcion.setVista(this);
	}
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	public void comenzar() {
		int ordinalOpcion;
		//Bucle que solo acaba al seleccionar la opcion salir. Ordinal 0
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}
	public void salir() {
		controlador.terminar();
	}
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		
		try {
			Aula aula = Consola.leerAula();
			controlador.insertarAula(aula);
			System.out.println("Se ha insertado el aula.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	public void borrarAula() { 
		Consola.mostrarCabecera("Borrar aula");
		
		try {
			controlador.borrarAula(Consola.leerAula());
			System.out.println("Se ha borrado el aula.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}	
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		
		try {
			Aula aulaBuscar = Consola.leerAula();
			controlador.buscarAula(aulaBuscar);
			if (aulaBuscar != null) {/*Si no se verifica que no es null, al buscar dará el resultado correcto y 
			tambien tantos valores null como capacidad de array restantes.*/
				aulaBuscar.toString();
			} else {
				System.out.println("No se ha encontrado el aula.");
			}
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	public void listarAulas() {
		Consola.mostrarCabecera("Listar aulas");
		//Se crea una lista de strings y se llama al metodo para representar.
		List<String> string = controlador.representarAulas();
		Iterator<String> I = string.iterator();
		if (string.size() > 0) {
			for (string.iterator(); I.hasNext();) {
				String string2 = I.next();
				System.out.println(string2);
			}
		} else {
			System.out.println("No hay ningún aula registrada.");
		}
	}
	
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		
		try {
			Profesor profesor = Consola.leerProfesor();
			controlador.insertarProfesor(profesor);
			System.out.println("Se ha insertado el profesor.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		
		try {
			Profesor profesorBorrar = Consola.leerProfesor();
			controlador.borrarProfesor(profesorBorrar);
			System.out.println("Se ha borrado el profesor.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		
		try {
			Profesor profesorBuscar = Consola.leerProfesor();
			controlador.buscarProfesor(profesorBuscar);
			if (profesorBuscar != null) {
			} else {
				System.out.println("No se ha encontrado el profesor.");
			}
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	public void listarProfesores() {
		Consola.mostrarCabecera("Listar profesores");
		
		List<String> string = controlador.representarProfesores();
		Iterator<String> I = string.iterator();
		if (string.size() > 0) {//Si la lista tiene al menos un indice, lista profesores, si no, no lista.
			for (string.iterator(); I.hasNext();) {
				String string2 = I.next();
				System.out.println(string2);
			}
		} else {
			System.out.println("No hay ningún profesor registrado.");
		}
	}
	
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		
		try {
			Profesor profesor = Consola.leerProfesor();
			Reserva reserva = leerReserva(profesor);
			controlador.realizarReserva(reserva);
			System.out.println("Se ha realizado la reserva.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	private Reserva leerReserva(Profesor profesor) {
		
		Aula aula = Consola.leerAula();
		Permanencia permanencia = new Permanencia(Consola.leerDia(),Consola.leerTramo());
		Reserva reserva = new Reserva(profesor, aula, permanencia);
		
		return reserva;
	}
	
	public void anularReserva() {
		Consola.mostrarCabecera("Anular reserva");
		
		try {
			Profesor profesor = Consola.leerProfesor();
			Reserva reserva = leerReserva(profesor);
			controlador.anularReserva(reserva);
			System.out.println("Se ha realizado la reserva.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	
	public void listarReservas() {
		Consola.mostrarCabecera("Listar reservas");
		
		List<String> string = controlador.representarReservas();
		Iterator<String> I = string.iterator();
		if (string.size() > 0) {
			for (string.iterator(); I.hasNext();) {
				String string2 = I.next();
				System.out.println(string2);
			}
		} else {
			System.out.println("No hay ningún aula registrada.");
		}
	}
	
	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas por aula");
		
		Aula aulaReserva = Consola.leerAula();
		List<Reserva> reservas = controlador.getReservasAula(aulaReserva);
		Iterator<Reserva> I = reservas.iterator();
		if (reservas.size() > 0) {
			for (reservas.iterator(); I.hasNext();) {
				Reserva reserva = I.next();
				if (reserva !=null) {
					System.out.println(reserva);
				}
			}
			
		} else {
			System.out.println("No hay reservas para este aula.");
		}
	}
	
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listar reservas por profesor");
		
		Profesor profesor = Consola.leerProfesor();
		List<Reserva> reservas = controlador.getReservasProfesor(profesor);
		Iterator<Reserva> I = reservas.iterator();
		if(reservas.size() > 0) {
			for (reservas.iterator(); I.hasNext();) {
				Reserva reserva = I.next();
				if (reserva != null) {
					System.out.println(reserva);
				}
			}
		} else {
			System.out.println("Este profesor no tiene ninguna reserva");
		}
		}
	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("Listar reservas por permanencia");
		
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		List<Reserva> reservas = controlador.getReservasPermanencia(permanencia);
		Iterator<Reserva> I = reservas.iterator();
		if(reservas.size() > 0) {
			for (reservas.iterator(); I.hasNext();) {
				Reserva reserva = I.next();
				if (reserva != null) {
					System.out.println(reserva);
				}
			}
		} else {
			System.out.println("No hay ninguna reserva para esta permanencia");
		}
	}
	
	public void consultarDisponibilidad() {
		//Se lee aula y permanencia. Se compara utilizando el metodo. Si esta disponible sera true.
		Aula aula = Consola.leerAula();
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		if (controlador.consultarDisponibilidadAula(aula, permanencia) == true) {
			System.out.println("Este aula esta disponible en la permanencia introducida");
		} else{
			System.out.println("Este aula no esta disponible durante la permanencia introducida");
		};
	}	
}