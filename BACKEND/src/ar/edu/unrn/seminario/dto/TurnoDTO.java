package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;
import java.util.Date;

import ar.edu.unrn.seminario.modelo.Atencion;
import ar.edu.unrn.seminario.modelo.Paciente;

public class TurnoDTO {
	private int idTurno;
	private LocalDateTime fecha;
	private UsuarioDTO medico;
	private Paciente paciente;
	private Atencion atencion;
	
	public TurnoDTO() {}
	public TurnoDTO(int idTurno, LocalDateTime fecha, UsuarioDTO medico, Paciente paciente) {
		super();
		this.idTurno = idTurno;
		this.fecha = fecha;
		this.medico = medico;
		this.paciente = paciente;
		atencion = new Atencion();
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
	public UsuarioDTO getMedico() {
		return medico;
	}
	public void setMedico(UsuarioDTO medico) {
		this.medico = medico;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Atencion getAtencion() {
		return atencion;
	}
	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	
	
	
}
