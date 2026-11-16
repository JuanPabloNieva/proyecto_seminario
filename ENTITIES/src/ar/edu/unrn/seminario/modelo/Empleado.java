package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

public class Empleado extends Persona {
	private Usuario usuario;

	public Empleado(String nombre, String apellido, int dni, Usuario usuario) throws DataEmptyException, DniErroneoException {
		super(nombre, apellido, dni);
		this.usuario = usuario;
	}
}
