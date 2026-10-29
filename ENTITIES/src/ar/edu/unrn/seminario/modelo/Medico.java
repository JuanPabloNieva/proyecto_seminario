package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.GeneradorID;

public class Medico extends Persona {
	private Usuario usuario;
	private String matricula;
	private Especialidad especialidad;
	private List<Turno> turnosDisponibles = new ArrayList<>();

	public Medico(String nombre, String apellido, int dni, String matricula, Especialidad especialidad)
			throws DataEmptyException {
		super(nombre, apellido, dni);
		this.especialidad = especialidad;
		this.matricula = matricula;
		this.turnosDisponibles = generarTurnosSemanales();
	}

	public List<Turno> generarTurnosSemanales() throws DataEmptyException {

		List<Turno> turnos = new ArrayList<>();

		int id;

		LocalDateTime inicio = LocalDate.now().atTime(9, 00);
		LocalDateTime fin = inicio.plusWeeks(1);

		LocalTime horaInicio = LocalTime.of(9, 0);
		LocalTime horaFin = LocalTime.of(18, 0);
		int duracionTurno = 30;

		LocalDateTime control = inicio;

		while (control.isBefore(fin)) {
			LocalTime horaTurno = control.toLocalTime();
			LocalDate fechaTurno = control.toLocalDate();
			LocalDateTime fyhTurno = LocalDateTime.of(fechaTurno, horaTurno);

			if ((horaTurno.equals(horaInicio) || horaTurno.isAfter(horaInicio)) && horaTurno.isBefore(horaFin)) {
				id = GeneradorID.obtenerSiguienteId();
				if (fyhTurno.isBefore(LocalDateTime.now())) {
					turnos.add(new Turno(id, control, this.getMatricula(), false));
				} else {
					turnos.add(new Turno(id, control, this.getMatricula(), true));
				}

			}

			control = control.plusMinutes(duracionTurno);
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
