package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Turno;

public class PacienteDTO {
	private String nombre;
	private String apellido;
	private int dni;
	private String obraSocial;
	private List<Turno> historiaClinica;
	
	public PacienteDTO(String nombre, String apellido, int dni, String obraSocial, List<Turno> historiaClinica) {
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

	public List<Turno> getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(List<Turno> historiaClinica) {
		this.historiaClinica = historiaClinica;
	}
	
	
}
