package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

public class Turno {
	private int idTurno;
	private LocalDateTime fecha;
	private Medico medico;
	private Paciente paciente;
	private Atencion detalle;
	private boolean activo;
	
	public Turno(int idTurno, LocalDateTime fecha, Medico medico, Paciente paciente, Atencion detalle, boolean activo) {
		this.idTurno = idTurno;
		this.fecha = fecha;
		this.medico = medico;
		this.paciente = paciente;
		this.detalle = detalle;
		this.activo = activo;
	}

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Atencion getDetalle() {
		return detalle;
	}

	public void setDetalle(Atencion detalle) {
		this.detalle = detalle;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}
