package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.modelo.Turno;

public class HistoriaMapper {
	
	public List<TurnoDTO> transformar(List<Turno> historiaClinica) {
		return historiaClinica.stream().map(turno -> new TurnoDTO(turno.getIdTurno(), turno.getFecha(),
				turno.getMatriculaMedico(), turno.getDniPaciente(), turno.isActivo())).collect(Collectors.toList());
	}
}
