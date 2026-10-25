package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;

public class Paciente extends Persona{
	private ObraSocial obraSocial;
	private List<Turno> historiaClinica;
	
	public Paciente(String nombre, String apellido, int dni, ObraSocial obraSocial) throws DataEmptyException {
		super(nombre, apellido, dni);
		
		validarCampoVacio(nombre, "Nombre");
		validarCampoVacio(apellido, "Apellido");
		validarCampoVacio(String.valueOf(dni), "Dni");
		
		this.obraSocial = obraSocial;
		this.historiaClinica = new ArrayList<>();
	}
	
	
	/*public Paciente(String nombre, String apellido, int dni, ObraSocial obraSocial, Turno turno) throws DataEmptyException {
		this(nombre, apellido, dni, obraSocial);
		this.historiaClinica.add(turno);
	}*/
	
	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}

	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}

	public List<Turno> getHistoriaClinica() {
		return historiaClinica;
	}

	public void setHistoriaClinica(List<Turno> historiaClinica) {
		this.historiaClinica = historiaClinica;
	}
	
	public void agregarTurnoHistoriaClinica(Turno t) {
		this.historiaClinica.add(t);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((String.valueOf(super.getDni()) == null) ? 0 : String.valueOf(super.getDni()).hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		if (String.valueOf(super.getDni()) == null) {
			if (String.valueOf(other.getDni()) != null)
				return false;
		} else if (!String.valueOf(super.getDni()).equals(String.valueOf(other.getDni())))
			return false;
		return true;
	}
	
}
