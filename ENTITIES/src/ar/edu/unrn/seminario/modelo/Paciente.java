package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona{
	private String obraSocial;
	private List<Turno> historiaClinica;
	
	public Paciente(String nombre, String apellido, int dni, String obraSocial) {
		super(nombre, apellido, dni);
		this.obraSocial = obraSocial;
		this.historiaClinica = new ArrayList<Turno>();
	}
	
	public Paciente(String nombre, String apellido, int dni, String obraSocial, Turno turno) {
		this(nombre, apellido, dni, obraSocial);
		this.historiaClinica.add(turno);
	}
}
