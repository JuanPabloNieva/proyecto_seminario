package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

public class Administrador extends Empleado {
	public Administrador(String nombre, String apellido, int dni, Usuario usuario) throws DataEmptyException, DniErroneoException {
		super(nombre, apellido, dni, usuario);
	}
}
