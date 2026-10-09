package calculadoraintegrales.backend.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.File;

public class Conexion {

    // 1. Método auxiliar para construir y asegurar la ruta del archivo
    private static String getRutaBaseDatos() {
        // Obtiene la carpeta principal del usuario (ej. C:\Users\NombreUsuario en Windows)
        String rutaCarpeta = System.getProperty("user.home") + File.separator + "CalculadoraCalculo";
        File carpeta = new File(rutaCarpeta);
        
        // Crea la carpeta "CalculadoraCalculo" si no existe en la computadora
        if (!carpeta.exists()) {
            carpeta.mkdirs(); 
        }
        
        // Retorna la cadena de conexión completa de SQLite
        return "jdbc:sqlite:" + rutaCarpeta + File.separator + "calculadora.db";
    }

    // 2. Método para abrir y obtener la conexión
    public static Connection obtener() throws SQLException {
        // DriverManager se encarga de instanciar la conexión con SQLite usando la ruta
        return DriverManager.getConnection(getRutaBaseDatos());
    }

    // 3. Método para crear las tablas (Se llamará al iniciar el programa)
public static void crearTablas() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nombre_usuario TEXT UNIQUE NOT NULL,"
                + " contrasena_hash TEXT NOT NULL,"
                + " fecha_registro TEXT DEFAULT CURRENT_TIMESTAMP"
                + " )";

        String sqlProcedimientos = "CREATE TABLE IF NOT EXISTS procedimientos ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_usuario INTEGER NOT NULL,"
                + " funcion TEXT NOT NULL,"
                + " variable TEXT NOT NULL,"
                + " tipo TEXT NOT NULL,"
                + " limite_inferior TEXT,"
                + " limite_superior TEXT,"
                + " resultado TEXT NOT NULL,"
                + " procedimiento TEXT,"
                + " fecha_hora TEXT DEFAULT CURRENT_TIMESTAMP,"
                + " FOREIGN KEY (id_usuario) REFERENCES usuarios(id)"
                + " )";

        try (Connection c = obtener(); Statement s = c.createStatement()) {
            // Usamos executeUpdate, que es el método correcto para DDL (Create, Drop, etc.)
            s.executeUpdate(sqlUsuarios);
            s.executeUpdate(sqlProcedimientos);
            System.out.println("Base de datos y tablas inicializadas correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
}
}