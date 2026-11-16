
package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.modelo.Especialidad;

public class TestEspecialidadDAO {

    public static void main(String[] args) {

        EspecialidadDAOJDBC especialidadDAO = new EspecialidadDAOJDBC();

        try {

            // CREAR
            Especialidad especialidad =
                    new Especialidad(4, "Especialidad de prueba");

            especialidadDAO.create(especialidad);

            System.out.println("Especialidad registrada correctamente.");

            // MODIFICAR
            especialidad.setNombre("Especialidad modificada");
            especialidad.setDescripcion("Descripción modificada");
            especialidad.setActivo(false);

            especialidadDAO.update(especialidad);

            System.out.println("Especialidad modificada correctamente.");

            // VERIFICAR MODIFICACIÓN
            Especialidad modificada = especialidadDAO.find(4);

            System.out.println("Nombre: " + modificada.getNombre());
            System.out.println("Descripción: " + modificada.getDescripcion());
            System.out.println("Activo: " + modificada.isActivo());

            // ELIMINAR
            especialidadDAO.remove(4);

            System.out.println("Especialidad eliminada correctamente.");

        } finally {
            ConnectionManager.disconnect();
        }
    }
}
