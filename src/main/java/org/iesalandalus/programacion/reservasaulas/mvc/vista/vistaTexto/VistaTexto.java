package org.iesalandalus.programacion.reservasaulas.mvc.vista.vistaTexto;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Consola;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class VistaTexto implements IVista {
	
	private static final String ERROR = "Ha habido un error";
	private static final String NOMBRE_VALIDO = "";
	private static final String CORREO_VALIDO = "[a-zñÑA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?(?:\\.[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?)";
	private IControlador controlador;
	
	public VistaTexto() {
		Opcion.setVista(this);
	}
	
	@Override
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}
	
	@Override
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
	
	@Override
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
			controlador.borrarAula(Consola.leerAulaFicticia());
			System.out.println("Se ha borrado el aula.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}	
	
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		
		try {
			Aula aulaBuscar = Consola.leerAulaFicticia();
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
			System.out.println("ERROR: No hay ningún aula registrada.");
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
			Profesor profesorBorrar = Consola.leerProfesorFicticio();
			controlador.borrarProfesor(profesorBorrar);
			System.out.println("Se ha borrado el profesor.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		
		try {
			Profesor profesorBuscar = Consola.leerProfesorFicticio();
			controlador.buscarProfesor(profesorBuscar);
			if (profesorBuscar != null) {
			} else {
				System.out.println("ERROR: No se ha encontrado el profesor.");
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
			System.out.println("ERROR: No hay ningún profesor registrado.");
		}
	}
	
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		
		Reserva reserva = null;
		
		try {
			
			reserva = Consola.leerReserva();
			
			controlador.realizarReserva(reserva);
			System.out.println("Se ha realizado la reserva.");
		} catch (OperationNotSupportedException e) {
			System.out.printf("%n%s", e.getMessage(), ERROR);
		}
	}
	
	public void anularReserva() {
		Consola.mostrarCabecera("Anular reserva");
		
		List<String> reservas = controlador.representarReservas();
		
		if(reservas == null) {
			System.out.println("No existe ninguna reserva en el sistema.");
		}else {
			
			try {
				
				Reserva reserva = Consola.leerReservaFicticia();
				controlador.anularReserva(reserva);
				System.out.println("Se ha anulado la reserva.");
			} catch (OperationNotSupportedException|IllegalArgumentException e) {
				System.out.printf("%n%s", e.getMessage(), ERROR);
			}
		}	
	}
	
	public void listarReservas() {

		Consola.mostrarCabecera("Listado de Reservas");

		List<String> reservas = controlador.representarReservas();
		// Comprobamos hay elementos en nuestra lista comprobando su tamaño
		if (reservas.size() > 0) {
			for (Iterator<String> it = reservas.iterator(); it.hasNext();) {
				String reserva = it.next();
				System.out.println(reserva);
			}
		} else {
			System.out.println("No hay reservas que listar. Debe insertar primero una reserva.");
		}
	}
	
	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas por aula");
		//Se utiliza leerAulaFicticia para buscar reserva por aula y asi no tener que introducir puestos.
		List<Reserva> reservas = controlador.getReservasAula(Consola.leerAulaFicticia());
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
		
		List<Reserva> reservas = controlador.getReservasProfesor(Consola.leerProfesorFicticio());
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

	public void consultarDisponibilidad() {
		//Se lee aula y permanencia. Se compara utilizando el metodo. Si esta disponible sera true.
		Aula aula = Consola.leerAulaFicticia(); //Se puede leer la ficticia ya que no hace falta saber los puestos.
		Permanencia permanencia = Consola.leerPermanencia();
		
		if (controlador.consultarDisponibilidadAula(aula, permanencia) == true) {
			System.out.println("Este aula esta disponible en la permanencia introducida");
			
		} else{
			System.out.println("Este aula no esta disponible durante la permanencia introducida");
		}
	}	
}