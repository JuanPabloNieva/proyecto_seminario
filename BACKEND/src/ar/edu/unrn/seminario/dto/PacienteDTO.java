package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

public class PacienteDTO {
	private String nombre;
	private String apellido;
	private int dni;
	private String obraSocial;
	private List<TurnoDTO> historiaClinica;
	
	public PacienteDTO(String nombre, String apellido, int dni, String obraSocial, List<TurnoDTO> historiaClinica) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.obraSocial = obraSocial;
		this.historiaClinica = historiaClinica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(String obraSocial) {
		this.obraSocial = obraSocial;
	}

	public List<TurnoDTO> getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(List<TurnoDTO> historiaClinica) {
		this.historiaClinica = historiaClinica;
	}
	
	
}
