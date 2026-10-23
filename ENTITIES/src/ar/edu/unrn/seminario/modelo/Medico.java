package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

public class Medico extends Persona{
	private Usuario usuario;
	private String matricula;
	private Especialidad especialidad;
	private List<Turno> turnosDisponibles;
	
	public Medico(String nombre, String apellido, int dni, String matricula, Especialidad especialidad) {
		super(nombre, apellido, dni);
		this.turnosDisponibles = new ArrayList<Turno>();
		this.especialidad = especialidad;
		this.matricula = matricula;
	}
	
	private void inicializarTurnosDisponibles() {
		
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
