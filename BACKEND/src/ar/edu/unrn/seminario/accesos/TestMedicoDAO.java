
package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;

public class TestMedicoDAO {

    public static void main(String[] args) {

        MedicoDao medicoDao = new MedicoDAOJDBC();

        try {
            EspecialidadDao especialidadDao = new EspecialidadDAOJDBC();

            Especialidad especialidad = especialidadDao.find(1);

            Medico medico = new Medico(
                "Juan",
                "Perez",
                30123456,
                "MAT100",
                especialidad
            );

            // Registrar médico y sus turnos.
            medicoDao.create(medico);

            System.out.println("MEDICO REGISTRADO");
            System.out.println("Nombre: " + medico.getNombre());
            System.out.println("Matrícula: " + medico.getMatricula());

            System.out.println(
                "Cantidad de turnos: " + medico.getTurnosDisponibles().size()
            );

            // Buscar médico.
            Medico encontrado = medicoDao.find("MAT100");

            System.out.println("\nMEDICO ENCONTRADO");
            System.out.println(encontrado.getNombre());

            // Mostrar sus turnos.
            for (ar.edu.unrn.seminario.modelo.Turno turno :
                    encontrado.getTurnosDisponibles()) {

                System.out.println(
                    "ID: " + turno.getIdTurno()
                    + " | Fecha: " + turno.getFecha()
                    + " | Activo: " + turno.isActivo()
                );
            }

            // Listar médicos.
            List<Medico> medicos = medicoDao.findAll();

            System.out.println("\nTOTAL DE MEDICOS: " + medicos.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
