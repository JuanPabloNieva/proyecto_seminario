
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.PersistenceException;
import ar.edu.unrn.seminario.modelo.Especialidad;

public class EspecialidadDAOJDBC implements EspecialidadDao {

    @Override
    public void create(Especialidad especialidad) {
        String sql = "INSERT INTO especialidades (codigo, nombre, descripcion, activo) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, especialidad.getCodigo());
                ps.setString(2, especialidad.getNombre());
                ps.setString(3, especialidad.getDescripcion());
                ps.setBoolean(4, especialidad.isActivo());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al registrar la especialidad", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Especialidad especialidad) {
        String sql = "UPDATE especialidades SET nombre = ?, descripcion = ?, activo = ? WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, especialidad.getNombre());
                ps.setString(2, especialidad.getDescripcion());
                ps.setBoolean(3, especialidad.isActivo());
                ps.setInt(4, especialidad.getCodigo());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al modificar la especialidad", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Integer codigo) {
        String sql = "DELETE FROM especialidades WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, codigo);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al eliminar la especialidad", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Especialidad find(Integer codigo) {
        String sql = "SELECT * FROM especialidades WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            Especialidad especialidad = null;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, codigo);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        especialidad = mapearEspecialidad(rs);
                    }
                }
            }

            return especialidad;

        } catch (SQLException e) {
            throw new PersistenceException("Error al buscar la especialidad", e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Especialidad> findAll() {
        List<Especialidad> especialidades = new ArrayList<Especialidad>();
        String sql = "SELECT * FROM especialidades";

        try {
            Connection conn = ConnectionManager.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    especialidades.add(mapearEspecialidad(rs));
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error al listar las especialidades", e);
        } finally {
            ConnectionManager.disconnect();
        }

        return especialidades;
    }

    private Especialidad mapearEspecialidad(ResultSet rs) throws SQLException {
        Especialidad especialidad = new Especialidad(rs.getInt("codigo"), rs.getString("nombre"));

        especialidad.setDescripcion(rs.getString("descripcion"));
        especialidad.setActivo(rs.getBoolean("activo"));

        return especialidad;
    }
}
