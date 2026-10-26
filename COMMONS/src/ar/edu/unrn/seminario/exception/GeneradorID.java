package ar.edu.unrn.seminario.exception;

public class GeneradorID {
	private static int contador = 0; 
	
	public static int obtenerSiguienteId() {
        contador++; 
        return contador;
    }

}
