package ar.edu.unrn.seminario.exception;

public class DniUnicoException extends RuntimeException{
	public DniUnicoException() {
		super();
	}
	
	public DniUnicoException(String message) {
		super(message);
	}
}
