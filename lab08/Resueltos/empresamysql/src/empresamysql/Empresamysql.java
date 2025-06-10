/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package empresamysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author WILLIAM
 */
public class Empresamysql {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:3306/empresamysql"; // Cambia "tu_base_de_datos" por el nombre real
        String usuario = "root"; // Usuario de MySQL
        String contraseña = "1234"; // Contraseña de MySQL

        // Intentar conectar
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("¡Conexión exitosa!");

            // Cerrar la conexión
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}