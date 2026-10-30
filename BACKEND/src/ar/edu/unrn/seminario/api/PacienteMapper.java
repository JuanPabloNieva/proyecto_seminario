package ar.edu.unrn.seminario.api;

import java.util.List;

import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Paciente;

public class PacienteMapper {

	private final HistoriaMapper hMapper = new HistoriaMapper();

	public PacienteDTO transformar(Paciente paciente) {

		List<TurnoDTO> historiaClinica = hMapper.transformar(paciente.getHistoriaClinica());

		return new PacienteDTO(paciente.getNombre(), paciente.getApellido(), paciente.getDni(),
				paciente.getObraSocial().getNombre(), historiaClinica);
	}

	public Paciente transformar(PacienteDTO pacienteDTO, List<Paciente> pacientes) {
		Paciente paciente = pacientes.stream().filter(p -> (p.getDni() == pacienteDTO.getDni())).findFirst()
				.orElse(null);
		return paciente;
	}

	public Paciente transformar(PacienteDTO pacienteDTO, ObraSocial os) throws DataEmptyException {
		return new Paciente(pacienteDTO.getNombre(), pacienteDTO.getApellido(), pacienteDTO.getDni(), os);
	}
}
