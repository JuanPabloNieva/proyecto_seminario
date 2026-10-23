package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona{
	private ObraSocial obraSocial;
	private List<Turno> historiaClinica;
	
	public Paciente(String nombre, String apellido, int dni, ObraSocial obraSocial) {
		super(nombre, apellido, dni);
		this.obraSocial = obraSocial;
		this.historiaClinica = new ArrayList<Turno>();
	}
	
	public Paciente(String nombre, String apellido, int dni, ObraSocial obraSocial, Turno turno) {
		this(nombre, apellido, dni, obraSocial);
		this.historiaClinica.add(turno);
	}

	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}

	public List<Turno> getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(List<Turno> historiaClinica) {
		this.historiaClinica = historiaClinica;
	}
	
	
}
