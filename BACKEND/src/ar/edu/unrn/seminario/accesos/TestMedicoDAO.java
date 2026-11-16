package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;

public class TestMedicoDAO {

    public static void main(String[] args) {

        MedicoDao medicoDao = new MedicoDAOJDBC();

        try {
            EspecialidadDao especialidadDao = new EspecialidadDAOJDBC();

            // Buscar especialidades.
            Especialidad cardiologia = especialidadDao.find(2);
            Especialidad dermatologia = especialidadDao.find(3);

            // Registrar segundo médico.
            Medico medico2 = new Medico(
                "Ana",
                "Gomez",
                28987654,
                "MAT200",
                cardiologia
            );

            medicoDao.create(medico2);

            // Registrar tercer médico.
            Medico medico3 = new Medico(
                "Pedro",
                "Lopez",
                32123456,
                "MAT300",
                dermatologia
            );

            medicoDao.create(medico3);

            System.out.println("MEDICOS REGISTRADOS");

            // Listar médicos.
            List<Medico> medicos = medicoDao.findAll();

            for (Medico medico : medicos) {
                System.out.println(
                    medico.getNombre() + " "
                    + medico.getApellido()
                    + " | Matrícula: " + medico.getMatricula()
                    + " | Turnos: " + medico.getTurnosDisponibles().size()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}