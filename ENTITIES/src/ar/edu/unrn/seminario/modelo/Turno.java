package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

public class Turno {
	private int idTurno;
	private LocalDateTime fecha;
	private Medico medico;
	private Paciente paciente;
	private Atencion detalle;
	private boolean activo;
}
