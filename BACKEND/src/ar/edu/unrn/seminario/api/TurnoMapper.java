package ar.edu.unrn.seminario.api;

import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.modelo.Turno;

public class TurnoMapper {
	public TurnoDTO transformar(Turno turno) {
		return new TurnoDTO(turno.getIdTurno(), turno.getFecha(), turno.getMatriculaMedico(), turno.getDniPaciente(),
				turno.isActivo());
	}

//	public Turno transformar(TurnoDTO turno) {
//		return 
//	}
}
