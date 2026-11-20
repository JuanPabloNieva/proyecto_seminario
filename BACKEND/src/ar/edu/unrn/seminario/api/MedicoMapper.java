package ar.edu.unrn.seminario.api;

import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.modelo.Medico;

public class MedicoMapper {

    public MedicoDTO transformar(Medico medico) {

        EspecialidadDTO especialidadDTO = new EspecialidadDTO(
                medico.getEspecialidad().getCodigo(),
                medico.getEspecialidad().getNombre()
        );

        return new MedicoDTO(
                medico.getNombre(),
                medico.getApellido(),
                medico.getDni(),
                medico.getMatricula(),
                especialidadDTO
        );
    }
}