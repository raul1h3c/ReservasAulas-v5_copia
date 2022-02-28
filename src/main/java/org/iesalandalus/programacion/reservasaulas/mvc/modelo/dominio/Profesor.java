package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

public class Profesor {
	
	private static final String ER_TELEFONO = "([6|9][0-9]{8})";
	private static final String ER_CORREO = "[a-zñÑA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?(?:\\.[a-zñÑA-Z0-9](?:[a-zñÑA-Z0-9-]{0,61}[a-zñÑA-Z0-9])?)";
	
	private String nombre;
	private String correo;
	private String telefono;
	
	public Profesor (String nombre, String correo) { //Constructor sin telefono
		
		setNombre(nombre);
		setCorreo(correo);
	}
	
	public Profesor (String nombre, String correo, String telefono) {
		
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
			
	}
	
	public Profesor (Profesor profesorC) {
		
		if(profesorC == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		
		setNombre(profesorC.getNombre());
		setCorreo(profesorC.getCorreo());
		setTelefono(profesorC.getTelefono());
	}
	
	
	public String getNombre() {
		return nombre;
	}
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		}
		if (nombre.isEmpty()) {
		throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");	
		}
		
		this.nombre = formateaNombre(nombre);
	}
	
	private String formateaNombre(String nombre) {
		
		char[] caracter = nombre.toCharArray(); //Se crea un array a partir de la string nombre
		caracter[0] = Character.toUpperCase(caracter[0]); //Se convierte la primera letra a mayuscula
		
		for (int i = 0; i < caracter.length -1; i++) {
			
			if(caracter[i] == ' ' || caracter[i] == '.')//Si una iteración es espacio o punto, la siguiente iteración sera mayuscula
				caracter[i+1] = Character.toUpperCase(caracter[i+1]);
		}
		String salida = new String(caracter);//A partir del array caracter se crea una string salida
		
		return salida.trim();//Para eliminar espacios se utiliza trim()
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) { //Matcher para comprobar que cumple el patron
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		if (telefono != null && !telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");	
		}
		this.telefono = telefono;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return String.format("Profesor: Nombre: %s, Correo: %s, Telefono: %s", nombre, correo, telefono);
	}	
}