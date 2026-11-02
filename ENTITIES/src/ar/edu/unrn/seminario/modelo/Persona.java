package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public abstract class Persona {
	private String nombre;
	private String apellido;
	private int dni;
	
	public Persona(String nombre, String apellido, int dni) throws DataEmptyException{
		validarCampoVacio(nombre, "Nombre");
		validarCampoVacio(apellido, "Apellido");
		validarCampoVacio(String.valueOf(dni), "Dni");
		
		this.nombre=nombre;
		this.apellido=apellido;
		this.dni=dni;
	}
	
	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws DataEmptyException{
		validarCampoVacio(nombre, "Nombre");
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) throws DataEmptyException{
		validarCampoVacio(apellido, "Apellido");
		this.apellido = apellido;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) throws DataEmptyException{
		validarCampoVacio(String.valueOf(dni), "Dni");
		this.dni = dni;
	}
	
	
}
