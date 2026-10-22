package ar.edu.unrn.seminario.exception;

public class UsuarioException extends RuntimeException{
	public UsuarioException() {
		
	}
	
	public UsuarioException(String message) {
		super(message);
	}
}
