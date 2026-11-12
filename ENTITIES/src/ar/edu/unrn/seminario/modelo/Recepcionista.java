package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public class Recepcionista extends Empleado {
	public Recepcionista(String nombre, String apellido, int dni, Usuario usuario) throws DataEmptyException {
		super(nombre, apellido, dni, usuario);
	}
}
