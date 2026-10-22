package ar.edu.unrn.seminario.dto;

public class ObraSocialDTO {
	private Integer codigo;
	private String nombre;
	private boolean activo;

	public ObraSocialDTO() {
		
	}

	public ObraSocialDTO(Integer codigo, String nombre, boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
