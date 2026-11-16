
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Paciente;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;

public class PacienteDAOJDBC implements PacienteDao {

    @Override
    public void create(Paciente paciente) {

        String sql = "INSERT INTO pacientes "
                + "(dni, nombre, apellido, obra_social) "
                + "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setInt(4, paciente.getObraSocial().getCodigo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el paciente", e);
        }
    }

    @Override
    public void update(Paciente paciente) {

        String sql = "UPDATE pacientes SET nombre = ?, "
                + "apellido = ?, obra_social = ? WHERE dni = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getObraSocial().getCodigo());
            ps.setInt(4, paciente.getDni());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar el paciente", e);
        }
    }

    @Override
    public void remove(Integer dni) {

        String sql = "DELETE FROM pacientes WHERE dni = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, dni);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el paciente", e);
        }
    }

    @Override
    public Paciente find(Integer dni) {

        String sql = "SELECT p.dni, p.nombre, p.apellido, "
                + "o.codigo, o.nombre AS nombre_obra, o.activo "
                + "FROM pacientes p "
                + "INNER JOIN obras_sociales o "
                + "ON p.obra_social = o.codigo "
                + "WHERE p.dni = ?";

        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, dni);

            ResultSet rs = ps.executeQuery();

            Paciente paciente = null;

            if (rs.next()) {
                paciente = mapearPaciente(rs);
            }

            rs.close();
            ps.close();

            return paciente;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el paciente", e);
        }
    }

    @Override
    public List<Paciente> findAll() {

        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT p.dni, p.nombre, p.apellido, "
                + "o.codigo, o.nombre AS nombre_obra, o.activo "
                + "FROM pacientes p "
                + "INNER JOIN obras_sociales o "
                + "ON p.obra_social = o.codigo";

        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los pacientes", e);
        }

        return pacientes;
    }

    private Paciente mapearPaciente(ResultSet rs) throws SQLException {

        try {
            ObraSocial obraSocial = new ObraSocial(
                    rs.getInt("codigo"),
                    rs.getString("nombre_obra")
            );

            obraSocial.setActivo(rs.getBoolean("activo"));

            return new Paciente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("dni"),
                    obraSocial
            );

        } catch (DataEmptyException | DniErroneoException e) {
            throw new IllegalStateException(
                    "No se pudo construir el paciente recuperado de la base de datos", e
            );
        }
    }
}
