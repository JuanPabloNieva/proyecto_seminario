package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;


public class Medico extends Persona {
	private Usuario usuario;
	private String matricula;
	private Especialidad especialidad;
	private List<Turno> turnosDisponibles = new ArrayList<>();

	public Medico(String nombre, String apellido, int dni, String matricula, Especialidad especialidad)
	        throws DataEmptyException, DniErroneoException {
	    super(nombre, apellido, dni);
	    this.especialidad = especialidad;
	    this.matricula = matricula;
	    this.turnosDisponibles = new ArrayList<>();
	}

	public List<Turno> generarTurnosSemanales() throws DataEmptyException {

	    List<Turno> turnos = new ArrayList<>();

	    LocalDateTime ahora = LocalDateTime.now();
	    LocalDateTime fin = ahora.plusWeeks(1);

	    LocalDate fechaActual = ahora.toLocalDate();

	    LocalTime horaInicio = LocalTime.of(9, 0);
	    LocalTime horaFin = LocalTime.of(18, 0);

	    LocalDateTime control = fechaActual.atTime(horaInicio);

	    while (control.isBefore(fin)) {

	        LocalTime horaTurno = control.toLocalTime();

	        if (!control.isBefore(ahora) && !horaTurno.isBefore(horaInicio) && horaTurno.isBefore(horaFin)) {

	            Turno turno = new Turno(0, control, this.getMatricula(), true);

	            turnos.add(turno);
	        }

	        control = control.plusMinutes(30);
	    }

	    return turnos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<Turno> getTurnosDisponibles() {
		return turnosDisponibles;
	}

	public void setTurnosDisponibles(List<Turno> turnosDisponibles) {
		this.turnosDisponibles = turnosDisponibles;
	}
}
