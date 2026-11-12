package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public class Administrador extends Empleado {
	public Administrador(String nombre, String apellido, int dni, Usuario usuario) throws DataEmptyException {
		super(nombre, apellido, dni, usuario);
	}
}
