package ar.edu.unrn.seminario.api;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.modelo.Medico;
import ar.edu.unrn.seminario.modelo.Turno;

public class MedicoMapper {

    public MedicoDTO transformar(Medico medico) {

        EspecialidadDTO especialidadDTO = new EspecialidadDTO( medico.getEspecialidad().getCodigo(), medico.getEspecialidad().getNombre()
        );

        List<TurnoDTO> turnosDTO = medico.getTurnosDisponibles().stream().map(t -> new TurnoDTO(t.getIdTurno(), t.getFecha(),
                        t.getMatriculaMedico(),t.getDniPaciente(),t.isActivo())).collect(Collectors.toList());

        return new MedicoDTO(medico.getNombre(),medico.getApellido(),medico.getDni(),medico.getMatricula(), especialidadDTO, turnosDTO);
    }
}
