package ar.edu.unrn.seminario.exception;

public class OperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationException(String mensaje) {
        super(mensaje);
    }
}