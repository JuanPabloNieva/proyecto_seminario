
package ar.edu.unrn.seminario.accesos;

public class TestPacienteDAO {

    public static void main(String[] args) {

        PacienteDAOJDBC pacienteDAO = new PacienteDAOJDBC();

        try {
            pacienteDAO.remove(34695322);

            System.out.println("Paciente eliminado correctamente.");

        } finally {
            ConnectionManager.disconnect();
        }
    }
}
