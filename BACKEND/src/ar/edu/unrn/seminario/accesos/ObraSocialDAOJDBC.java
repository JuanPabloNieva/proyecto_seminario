
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.ObraSocial;

public class ObraSocialDAOJDBC implements ObraSocialDao {

    @Override
    public void create(ObraSocial obraSocial) {

        String sql = "INSERT INTO obras_sociales " + "(codigo, nombre, activo) VALUES (?, ?, ?)";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, obraSocial.getCodigo());
            ps.setString(2, obraSocial.getNombre());
            ps.setBoolean(3, obraSocial.isActivo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar la obra social", e);
        }
    }

    @Override
    public void update(ObraSocial obraSocial) {

        String sql = "UPDATE obras_sociales SET nombre = ?, " + "activo = ? WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, obraSocial.getNombre());
            ps.setBoolean(2, obraSocial.isActivo());
            ps.setInt(3, obraSocial.getCodigo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar la obra social", e);
        }
    }

    @Override
    public void remove(Integer codigo) {

        String sql = "DELETE FROM obras_sociales WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, codigo);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar la obra social", e);
        }
    }

    @Override
    public ObraSocial find(Integer codigo) {

        String sql = "SELECT * FROM obras_sociales WHERE codigo = ?";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, codigo);

            ResultSet rs = ps.executeQuery();

            ObraSocial obraSocial = null;

            if (rs.next()) {
                obraSocial = mapearObraSocial(rs);
            }

            rs.close();
            ps.close();

            return obraSocial;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la obra social", e);
        }
    }

    @Override
    public List<ObraSocial> findAll() {

        List<ObraSocial> obrasSociales = new ArrayList<>();

        String sql = "SELECT * FROM obras_sociales";

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                obrasSociales.add(mapearObraSocial(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las obras sociales", e);
        }

        return obrasSociales;
    }

    private ObraSocial mapearObraSocial(ResultSet rs)
            throws SQLException {

        ObraSocial obraSocial = new ObraSocial(
                rs.getInt("codigo"),
                rs.getString("nombre")
        );

        obraSocial.setActivo(rs.getBoolean("activo"));

        return obraSocial;
    }
}
