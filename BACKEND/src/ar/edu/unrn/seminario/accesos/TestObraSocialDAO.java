
package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.modelo.ObraSocial;

public class TestObraSocialDAO {

    public static void main(String[] args) {

        ObraSocialDAOJDBC obraSocialDAO = new ObraSocialDAOJDBC();

        try {

            // CREAR
            ObraSocial obraSocial = new ObraSocial(4, "OBRA DE PRUEBA");
            obraSocial.setActivo(true);

            obraSocialDAO.create(obraSocial);

            System.out.println("Obra social registrada correctamente.");

            // MODIFICAR
            obraSocial.setNombre("OBRA MODIFICADA");
            obraSocial.setActivo(false);

            obraSocialDAO.update(obraSocial);

            System.out.println("Obra social modificada correctamente.");

            // VERIFICAR MODIFICACIÓN
            ObraSocial modificada = obraSocialDAO.find(4);

            System.out.println("Nombre: " + modificada.getNombre());
            System.out.println("Activo: " + modificada.isActivo());

            // ELIMINAR
            obraSocialDAO.remove(4);

            System.out.println("Obra social eliminada correctamente.");

        } finally {
            ConnectionManager.disconnect();
        }
    }
}
