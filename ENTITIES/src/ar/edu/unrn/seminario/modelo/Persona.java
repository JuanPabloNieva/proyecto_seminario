package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

public abstract class Persona {
	private String nombre;
	private String apellido;
	private int dni;
	
	public Persona(String nombre, String apellido, int dni) throws DataEmptyException, DniErroneoException{
		validarCampoVacio(nombre, "Nombre");
		validarCampoVacio(apellido, "Apellido");
		validarCampoVacio(String.valueOf(dni), "Dni");
		if (dni < 1000000 || dni > 99999999) {
	        throw new DniErroneoException("El DNI debe tener entre 7 y 8 dígitos.");
	    }
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
	public void setNombre(String nombre){
		
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
	
	
}
