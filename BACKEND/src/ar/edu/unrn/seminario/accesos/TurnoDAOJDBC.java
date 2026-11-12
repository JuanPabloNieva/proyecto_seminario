
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Turno;

public class TurnoDAOJDBC implements TurnoDao {

    @Override
    public void create(Turno turno) {
        String sql = "INSERT INTO turnos " + "(fecha, matricula_medico, dni_paciente, activo) "  + "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(turno.getFecha()));
            ps.setString(2, turno.getMatriculaMedico());

            if (turno.getDniPaciente() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, turno.getDniPaciente());
            }

            ps.setBoolean(4, turno.isActivo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el turno", e);
        }
    }

    @Override
    public void update(Turno turno) {
        String sql = "UPDATE turnos SET fecha = ?, matricula_medico = ?, " + "dni_paciente = ?, activo = ? WHERE id_turno = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(turno.getFecha()));
            ps.setString(2, turno.getMatriculaMedico());

            if (turno.getDniPaciente() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, turno.getDniPaciente());
            }

            ps.setBoolean(4, turno.isActivo());
            ps.setInt(5, turno.getIdTurno());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el turno", e);
        }
    }

    @Override
    public void remove(Integer idTurno) {
        String sql = "DELETE FROM turnos WHERE id_turno = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idTurno);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el turno", e);
        }
    }

    @Override
    public Turno find(Integer idTurno) {
        String sql = "SELECT * FROM turnos WHERE id_turno = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, idTurno);

            ResultSet rs = ps.executeQuery();

            Turno turno = null;

            if (rs.next()) {
                turno = mapearTurno(rs);
            }

            rs.close();
            ps.close();

            return turno;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el turno", e);
        }
    }

    @Override
    public List<Turno> findAll() {
        String sql = "SELECT * FROM turnos ORDER BY fecha";

        List<Turno> turnos = new ArrayList<Turno>();

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los turnos", e);
        }

        return turnos;
    }

    @Override
    public List<Turno> findByMedico(String matricula) {
        String sql = "SELECT * FROM turnos " + "WHERE matricula_medico = ? ORDER BY fecha";

        List<Turno> turnos = new ArrayList<Turno>();

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, matricula);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                turnos.add(mapearTurno(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar los turnos del médico", e);
        }

        return turnos;
    }

    private Turno mapearTurno(ResultSet rs) throws SQLException {
        try {
            int id = rs.getInt("id_turno");
            Timestamp fecha = rs.getTimestamp("fecha");
            String matricula = rs.getString("matricula_medico");

            int dni = rs.getInt("dni_paciente");
            Integer dniPaciente = rs.wasNull() ? null : dni;

            boolean activo = rs.getBoolean("activo");

            return new Turno(
                id,
                fecha.toLocalDateTime(),
                matricula,
                dniPaciente,
                activo
            );

        } catch (DataEmptyException e) {
            throw new SQLException("Error al construir el turno", e);
        }
    }
}
