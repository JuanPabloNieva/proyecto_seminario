package ar.edu.unrn.seminario.modelo;

public class Especialidad {
	private String nombre;
	private String descripcion;
	private Integer codigo;
	private boolean activo;
	
	public Especialidad(Integer codigo, String nombre) {
		this.nombre = nombre;
		this.descripcion = nombre;
		this.codigo = codigo;
		this.activo = true;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
}
