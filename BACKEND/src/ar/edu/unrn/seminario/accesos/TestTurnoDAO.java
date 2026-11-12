
package ar.edu.unrn.seminario.accesos;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Turno;

public class TestTurnoDAO {

    public static void main(String[] args) {

        TurnoDao turnoDao = new TurnoDAOJDBC();

        try {
            // 1. BUSCAR UN TURNO EXISTENTE
            Turno turno = turnoDao.find(19);

            System.out.println("TURNO ENCONTRADO");
            System.out.println("ID: " + turno.getIdTurno());
            System.out.println("Fecha: " + turno.getFecha());
            System.out.println("Matrícula: " + turno.getMatriculaMedico());
            System.out.println("Activo: " + turno.isActivo());

            // 2. LISTAR TODOS LOS TURNOS
            List<Turno> turnos = turnoDao.findAll();

            System.out.println("\nLISTADO DE TURNOS");
            System.out.println("Cantidad: " + turnos.size());

            // 3. BUSCAR TURNOS DE UN MÉDICO
            List<Turno> turnosMedico =
                    turnoDao.findByMedico("MAT100");

            System.out.println("\nTURNOS DEL MEDICO MAT100");
            System.out.println("Cantidad: " + turnosMedico.size());

            // 4. MODIFICAR UN TURNO
            turno.setActivo(false);
            turnoDao.update(turno);

            Turno modificado = turnoDao.find(19);

            System.out.println("\nTURNO MODIFICADO");
            System.out.println("Activo: " + modificado.isActivo());

            // 5. ELIMINAR UN TURNO
            turnoDao.remove(19);

            Turno eliminado = turnoDao.find(19);

            System.out.println("\nTURNO ELIMINADO");
            System.out.println("Resultado: " + eliminado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
