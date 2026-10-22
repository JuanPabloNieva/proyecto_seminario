package ar.edu.unrn.seminario.modelo;

public class Empleado extends Persona{
	private Usuario usuario;
	
	public Empleado(String nombre, String apellido, int dni, Usuario usuario) {
		super(nombre, apellido, dni);
		this.usuario = usuario;
	}
}
