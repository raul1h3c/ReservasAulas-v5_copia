package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Consola() {
	}
	public static void mostrarMenu() {
		
		mostrarCabecera("Reserva de aulas");
		for (Opcion opcionMenu : Opcion.values()) {
			System.out.println(opcionMenu);
		}
		}
	public static void mostrarCabecera(String cabecera) {
		//Este metodo muestra un mensaje con formato, es util para mostrar cabezera en cada metodo y que de un mensaje distinto
		System.out.printf("%n%s", cabecera);
		System.out.printf("%n----------------%n");
		}
	public static int elegirOpcion(){
		//Pide int mientras no se corresponda con un ordinal valido.
		int opcion;
		do {
			System.out.println("Elige una opción: ");
			opcion = Entrada.entero();
		} while(!Opcion.esOrdinalValido(opcion));
		
		return opcion;
	}
	public static Aula leerAula() {
		
		String nombre = leerNombreAula();
		int puestos = leerNumeroPuestos();
		
		return new Aula(nombre,puestos);
	}
	
	public static int leerNumeroPuestos() {
		int puestos;
		do {
			System.out.println("Introduce el número de puestos (10-50).");
			puestos = Entrada.entero();			
		} while (puestos < 10 || puestos > 50);
		return puestos;
	}
	
	public static Aula leerAulaFicticia() {
		
		System.out.println("Introduce un nombre de aula. ");
		String nombre = Entrada.cadena();
		return Aula.getAulaFicticia(nombre);
	}
		
	public static String leerNombreAula() {
		
		System.out.println("Introduce el nombre del aula: ");
		String nombreAula = Entrada.cadena();
		
		return nombreAula;
	}
	public static Profesor leerProfesor() {
		
		String nombre = leerNombreProfesor();
		String correo;
		String telefono;
		
		do {
			System.out.println("Introduce el email del profesor: ");
			correo = Entrada.cadena();
		}while(!correo.matches("[a-zñÑA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?(?:\\.[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?)"));
		//Se pide un correo y telefono mientras estos no sean validos
		do {
			System.out.println("Introduce el número de telefono o pulsa ENTER para dejarlo en blanco.");
			telefono = Entrada.cadena();
		} while (!telefono.isBlank() && !telefono.matches("([6|9][0-9]{8})"));
		
		Profesor profesor = (telefono.isBlank()||telefono == null)
		? new Profesor(nombre, correo) : new Profesor(nombre, correo, telefono);
		/*Un if else. Si el telefono esta en blanco o es nulo, se utiliza el constructor sin telefono
		 * y si no se dan esas condiciones significa que existe un telefono valido por lo que se utiliza el constructor con 3 parametros
		 */
		return profesor;
	}
	public static String leerNombreProfesor() {
		
		System.out.println("Introduce el nombre del profesor: ");
		String nombre = Entrada.cadena();
		
		return nombre;
	}
	
	public static Profesor leerProfesorFicticio() {
		
		String correo;
		do {
			System.out.println("Introduce un correo. ");
			correo = Entrada.cadena();
		} while(!correo.matches("[a-zñÑA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?(?:\\.[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?)"));
		return Profesor.getProfesorFicticio(correo);
	}
	public static Tramo leerTramo() {
		
		int opcion;
		do {
			System.out.println("Elige un tramo.");
			System.out.println("0. Mañana");
			System.out.println("1. Tarde");
			opcion = Entrada.entero();
		} while (opcion != 0 && opcion != 1);
		
		return Tramo.values()[opcion];//Devuelve la opcion del ordinal elegido, 0 o 1.
	}
	public static LocalDate leerDia() { 
	
		LocalDate dia = null;
		
		do {
		System.out.println("Introduce una fecha (Formato: dd/MM/yyyy)");
		
		try {
			dia = LocalDate.parse(Entrada.cadena(),(FORMATO_DIA));
		} catch (DateTimeParseException e) {
			dia = null;
		}
		} while(dia == null);
		return dia;//Para que el dia se devuelva en el formato, hay que utilizar formatter en el metodo. (leerDia().format(FORMATO_DIA)
	}
	
	private static LocalTime leerHora() {
		LocalTime hora = null;
		
		do {
			System.out.println("Introduce una hora en punto (Formato: HH:mm)");
			
			try {
				hora = LocalTime.parse(Entrada.cadena(), DateTimeFormatter.ofPattern("HH:mm"));
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: La hora introducida no es válida.");
				hora = null;
			} 
		} while(hora == null || hora.getMinute() != 0);//Lee hora hasta que sea una hora en punto.
		return hora;
	}
	
	public static int elegirPermanencia() {
		
		System.out.println("Elige una tipo de permanencia.");
		System.out.println("1. Por dia y hora.");
		System.out.println("2. Por dia y tramo.");
		int opcion;
		do {
			opcion = Entrada.entero();
		} while (opcion != 1 && opcion != 2);
		return opcion;
	}
	
	public static Permanencia leerPermanencia() {
		
		Permanencia permanencia = null;
		Permanencia permanenciaC = null;
		
		int opcion = elegirPermanencia();
		//Se utiliza el metodo para leer la opcion y dependiendo de esta se crea una nueva permanencia de un tipo u otro.
		if(opcion == 1) {
			permanencia = new PermanenciaPorHora(leerDia(), leerHora());
		} else if (opcion == 2) {
			permanencia = new PermanenciaPorTramo(leerDia(), leerTramo());
		}
		//Para evitar aliasing se devuelven copias.
		if (permanencia instanceof PermanenciaPorHora) {
			permanenciaC = new PermanenciaPorHora((PermanenciaPorHora)permanencia);
		} else if (permanencia instanceof PermanenciaPorTramo) {
			permanenciaC = new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		}
		return permanenciaC;
	}
	
	public static Reserva leerReserva() {
		return new Reserva(leerProfesorFicticio(), leerAulaFicticia(), leerPermanencia());
	}
	
	public static Reserva leerReservaFicticia() {
		Profesor profesor = new Profesor("Nombre", "correo@electroni.co", "694099124");
		
		return new Reserva(profesor, leerAulaFicticia(), leerPermanencia());
	}
}