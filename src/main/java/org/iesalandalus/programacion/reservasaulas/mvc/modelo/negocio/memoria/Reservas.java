package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas {
	
	private static final float MAX_PUNTOS_PROFESOR_MES = (float) 200.0;
	private List<Reserva> coleccionReservas;
	
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}
	
	public Reservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas");
		}
		setReservas(reservas);
	}
	
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		coleccionReservas = copiaProfundaReservas(reservas.getReservas());
	}
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		
		List<Reserva> copiaReservas = new ArrayList<>();
		
		for(Iterator<Reserva> I = reservas.iterator(); I.hasNext();) {
			Reserva reserva = I.next();
			copiaReservas.add(new Reserva(reserva));
		}
		return reservas;
	}
	
	@Override
	public List<Reserva> getReservas(){
		
		List<Reserva> reservasOrdenadas = copiaProfundaReservas(coleccionReservas);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {//Funcion lambda
			int comparacion = -1;
			if(p1.getDia().equals(p2.getDia())) {//Compara que los 2 dias son el mismo.
				
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
				/*Si ambas permanencias son del tipo por tramo, busca el ordinal del tramo, 0 Mañana, 1 Tarde
				y los compara.*/
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(), ((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					//Lo mismo pero con hora sin ordinal.
					comparacion = ((PermanenciaPorHora)p1).getHora().compareTo(((PermanenciaPorHora)p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());//Si el dia no es el mismo devuelve la comparacion de dias.
			}
		return comparacion;
	};
	reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
	return reservasOrdenadas;//Ordena comparando por aula primero y luego por permanencia.
	}
	
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();
	}
	
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		//Se utiliza para hacer las comprobaciones.
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
		} 
		
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		
		if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("ERROR: El profesor no tiene puntos suficientes.");
		}
		Reserva reserva2 = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		
		if (reserva2 != null) {
			
			if (reserva2.getPermanencia() instanceof PermanenciaPorTramo && reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
			//Lanza excepcion si se intenta introducir una permanencia de un tipo cuando ya existe permanencia de otro tipo.
			if (reserva2.getPermanencia() instanceof PermanenciaPorHora && reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		}
		else {
			coleccionReservas.add(new Reserva(reserva));
		}
	}
	
	private boolean esMesSiguienteOPosterior(Reserva reserva) {

		boolean resultado;
		
		LocalDate dia = reserva.getPermanencia().getDia();
		LocalDate mesSiguiente = LocalDate.now().plusMonths(1);//plusMonths para sumar 1 mes y conseguir el mes siguiente.
		LocalDate primerDiaMesSiguiente = LocalDate.of(mesSiguiente.getYear(), mesSiguiente.getMonth(), 1);
		
		if (dia.isAfter(primerDiaMesSiguiente) || dia.equals(primerDiaMesSiguiente)) {
			resultado = true;//True si es el dia 1 o despues del 1 del mes siguiente.
		} else resultado = false;
		return resultado;
	}
	
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntos = 0;
		
		for (Reserva reserva2 : getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia())) {
			puntos += reserva2.getPuntos();
		}
		return puntos + reserva.getPuntos();
	}
	
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mes){
		
		if (profesor == null) {
			throw new NullPointerException("El profesor no puede ser nulo");
		}
		
		List<Reserva> reservasProfesor = new ArrayList<>();
		
		
		for(Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
			Reserva reserva = I.next();
			LocalDate dia = reserva.getPermanencia().getDia();
			if(reserva.getProfesor().equals(profesor) && dia.getMonthValue() == mes.getMonthValue()
					&& dia.getYear() == mes.getYear()) {//Compara profesor, mes y dia del año.
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}
	
	private Reserva getReservaAulaDia(Aula aula, LocalDate dia) {
			if (dia == null) {
				throw new NullPointerException("El dia no puede ser nulo");
			}
			for(Reserva reserva : coleccionReservas) {
				LocalDate dia2 = reserva.getPermanencia().getDia();
				Aula aula2 = reserva.getAula();
				if(dia2.equals(dia) && aula2.equals(aula2)) {
					return reserva;
				}
			}
			return null;
	}
	@Override
	public Reserva buscar(Reserva reserva) {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			System.out.println("Se ha encontrado la reserva en el índice " + coleccionReservas.indexOf(reserva) + "  ");
			return new Reserva(reserva);
		} else {
			System.out.println("No se ha encontrado la reserva.");
			return null;
		}
	}
	@Override
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		}//El test la pone como IllegalArgumentException y me da fallo si la pongo como NullPointerException que es el tipo de excepcion correcto en este caso.
		
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		}
		
		if (coleccionReservas.contains(reserva)) {
			coleccionReservas.remove(reserva);
		}
		
		else if (!coleccionReservas.contains(reserva)){
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		}		
	}
	
	@Override
	public List<String> representar() {
		
		List<String> representar = new ArrayList<>();	
		
		for(Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
			representar.add(I.next().toString());
		}
		return representar;
	}
	
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		
		List<Reserva> reservas = new ArrayList<>();
		
		for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();
			
			if(reserva.getAula().equals(aula)) {
				reservas.add(new Reserva(reserva));
			}
		}
		return reservas;
	}
	
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		
		List<Reserva> reservasProfesor = new ArrayList<>();
	
		for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();//Le va dando el valor del iterator a reserva
			
			if(reserva.getProfesor().equals(profesor)) {//Si el profesor coincide, añade una reserva a la lista que luego mostrará
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {//Funcion lambda
			int comparacion = -1;
			if(p1.getDia().equals(p2.getDia())) {//Compara que los 2 dias son el mismo.
				
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
				/*Si ambas permanencias son del tipo por tramo, busca el ordinal del tramo, 0 Mañana, 1 Tarde
				y los compara.*/
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(), ((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					//Lo mismo pero con hora sin ordinal.
					comparacion = ((PermanenciaPorHora)p1).getHora().compareTo(((PermanenciaPorHora)p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());//Si el dia no es el mismo devuelve la comparacion de dias.
			}
		return comparacion;
	};
	reservasProfesor.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		
		return reservasProfesor;
	}
	
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
	
		List<Reserva> reservas = new ArrayList<>();
		
		for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
			
			Reserva reserva = I.next();
			
			if(reserva.getPermanencia().equals(permanencia)) {
				reservas.add(new Reserva(reserva));
			}
		}
		return reservas;
	}
	
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		
		boolean disponible = true;
		
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
			for (Iterator<Reserva> I = getReservas().iterator(); I.hasNext();) {
				
				Reserva reserva = I.next();//Recorre la lista con iterator y le pasa valores a reserva.
				if (reserva.getAula().equals(aula)&& reserva.getPermanencia().equals(permanencia)) {
					disponible = false;
				}  //Si el aula y la permanencia introducidas coinciden con con las existentes en la lista, devuelve falso.
		}
		return disponible;
	}
}