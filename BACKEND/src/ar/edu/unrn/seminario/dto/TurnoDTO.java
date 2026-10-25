package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;
import java.util.Date;

import ar.edu.unrn.seminario.modelo.Atencion;
import ar.edu.unrn.seminario.modelo.Paciente;

public class TurnoDTO {
	private int idTurno;
	private LocalDateTime fecha;
	private String matriculaMedico;
	private Integer dniPaciente;
	private Atencion atencion;
	private boolean activo;
	
	public TurnoDTO() {}
	
	public TurnoDTO(int idTurno, LocalDateTime fecha, String matriculaMedico, Integer dniPaciente, boolean activo) {
		super();
		this.idTurno = idTurno;
		this.fecha = fecha;
		this.matriculaMedico = matriculaMedico;
		this.dniPaciente = dniPaciente;
		atencion = new Atencion();
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
	public String getMedico() {
		return matriculaMedico;
	}
	public void setMedico(String matriculaMedico) {
		this.matriculaMedico = matriculaMedico;
	}
	public Integer getPaciente() {
		return dniPaciente;
	}
	public void setPaciente(Integer dniPaciente) {
		this.dniPaciente = dniPaciente;
	}
	public Atencion getAtencion() {
		return atencion;
	}
	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	public String getMatriculaMedico() {
		return matriculaMedico;
	}

	public void setMatriculaMedico(String matriculaMedico) {
		this.matriculaMedico = matriculaMedico;
	}

	public Integer getDniPaciente() {
		return dniPaciente;
	}

	public void setDniPaciente(Integer dniPaciente) {
		this.dniPaciente = dniPaciente;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	
	
}
