package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public class Empleado extends Persona {
	private Usuario usuario;

	public Empleado(String nombre, String apellido, int dni, Usuario usuario) throws DataEmptyException {
		super(nombre, apellido, dni);
		this.usuario = usuario;
	}
}
