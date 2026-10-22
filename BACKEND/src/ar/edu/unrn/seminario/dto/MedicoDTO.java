package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Turno;

public class MedicoDTO {
	private String nombre;
	private String apellido;
	private int dni;
	private String matricula;
	private Especialidad especialidad;
	private List<Turno> turnosDisponibles;
	
	
	
	public MedicoDTO(String nombre, String apellido, int dni, String matricula, Especialidad especialidad, List<Turno> turnos) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.matricula = matricula;
		this.especialidad = especialidad;
		this.turnosDisponibles = turnos;
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