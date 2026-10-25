package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public class Turno {
	private int idTurno;
	private LocalDateTime fecha;
	private String matriculaMedico;
	private Integer dniPaciente;
	private Atencion detalle;
	private boolean activo;
	
	public Turno(int idTurno, LocalDateTime fecha, String matricula, boolean activo) throws DataEmptyException {
		this.idTurno = idTurno;
		this.fecha = fecha;
		this.matriculaMedico = matricula;
		this.detalle = new Atencion();
		this.activo = activo;
	}
	
	public Turno(int idTurno, LocalDateTime fecha, String matricula, Integer dniPaciente, boolean activo) throws DataEmptyException {
		this(idTurno, fecha, matricula, activo);
		validarCampoVacio(String.valueOf(dniPaciente), "Dni");
		this.dniPaciente = dniPaciente;
	}

	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
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
