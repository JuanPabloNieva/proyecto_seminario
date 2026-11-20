
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;
import ar.edu.unrn.seminario.exception.PersistenceException;
import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;

public class MedicoDAOJDBC implements MedicoDao {

    @Override
    public void create(Medico medico) {
        String sql = "INSERT INTO medicos (matricula, dni, nombre, apellido, especialidad) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, medico.getMatricula());
                ps.setInt(2, medico.getDni());
                ps.setString(3, medico.getNombre());
                ps.setString(4, medico.getApellido());
                ps.setInt(5, medico.getEspecialidad().getCodigo());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al registrar el médico", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Medico medico) {
        String sql = "UPDATE medicos SET dni = ?, nombre = ?, apellido = ?, especialidad = ? WHERE matricula = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, medico.getDni());
                ps.setString(2, medico.getNombre());
                ps.setString(3, medico.getApellido());
                ps.setInt(4, medico.getEspecialidad().getCodigo());
                ps.setString(5, medico.getMatricula());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al modificar el médico", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String matricula) {
        String sql = "DELETE FROM medicos WHERE matricula = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, matricula);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al eliminar el médico", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Medico find(String matricula) {
        String sql = "SELECT m.*, e.nombre AS nombre_especialidad, e.descripcion, e.activo AS especialidad_activa, "
        		+ "e.codigo AS codigo_especialidad FROM medicos m INNER JOIN especialidades e ON m.especialidad = e.codigo WHERE m.matricula = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            Medico medico = null;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, matricula);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        medico = mapearMedico(rs);
                    }
                }
            }

            return medico;

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar el médico", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Medico> findAll() {
        String sql = "SELECT m.*, e.nombre AS nombre_especialidad, e.descripcion, e.activo AS especialidad_activa, "
        		+ "e.codigo AS codigo_especialidad FROM medicos m INNER JOIN especialidades e ON m.especialidad = e.codigo";

        List<Medico> medicos = new ArrayList<Medico>();

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Medico medico = mapearMedico(rs);
                    medicos.add(medico);
                }
            }

            return medicos;

        } catch (SQLException e) {
            throw new PersistenceException("Error al listar los médicos", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    private Medico mapearMedico(ResultSet rs) throws SQLException {
        try {
            Especialidad especialidad = new Especialidad(rs.getInt("codigo_especialidad"), rs.getString("nombre_especialidad"));
            especialidad.setDescripcion(rs.getString("descripcion"));
            especialidad.setActivo(rs.getBoolean("especialidad_activa"));

            return new Medico(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"), rs.getString("matricula"), especialidad);

        } catch (DataEmptyException | DniErroneoException e) {
            throw new SQLException("Error al construir el médico", e);
        }
    }
}
