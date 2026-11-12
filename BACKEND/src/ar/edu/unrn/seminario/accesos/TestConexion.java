
package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConexion {

    public static void main(String[] args) {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jdbc_seminario";
        String user = "root";
        String pass = "";

        try {
            Class.forName(driver);

            System.out.println("Driver JDBC cargado correctamente.");

            Connection conexion =
                DriverManager.getConnection(url, user, pass);

            System.out.println("Conexion exitosa con MySQL!");

            conexion.close();

        } catch (ClassNotFoundException e) {

            System.out.println("No se encontro el driver JDBC.");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Error al conectar con MySQL.");
            e.printStackTrace();
        }
    }
}
