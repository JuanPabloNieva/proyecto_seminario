package ar.edu.unrn.seminario.modelo;

import java.util.List;

public class Medico extends Persona{
	private Usuario usuario;
	private String matricula;
	private Especialidad especialidad;
	private List<Turno> turnosDisponibles;
	
	public Medico(String nombre, String apellido, int dni, String matricula) {
		super(nombre, apellido, dni);
		this.matricula = matricula;
	}
}
