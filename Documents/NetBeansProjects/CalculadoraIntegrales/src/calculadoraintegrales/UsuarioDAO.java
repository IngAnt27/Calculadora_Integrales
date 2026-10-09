package calculadoraintegrales;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
import calculadoraintegrales.Usuario;
import calculadoraintegrales.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método 1: Registra un nuevo usuario en la base de datos
    // Retorna true si tuvo éxito, o false si falló (por ejemplo, si el usuario ya existe)
    public boolean registrarUsuario(Usuario usuario) {
        // Usamos ? como comodines por seguridad (evita inyección SQL)
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena_hash) VALUES (?, ?)";
        
        try (Connection con = Conexion.obtener(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Reemplazamos los ? con los datos del objeto Usuario
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenaHash());
            
            // Ejecutamos la inserción
            ps.executeUpdate();
            return true; // Se guardó correctamente
            
        } catch (SQLException e) {
            // Si el nombre de usuario ya existe, SQLite lanzará un error porque le pusimos UNIQUE
            System.err.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    // Método 2: Verifica si el usuario y la contraseña coinciden
    // Retorna el objeto Usuario (con su ID) si es correcto, o null si las credenciales están mal
    public Usuario validarLogin(String nombreUsuario, String contrasenaHash) {
        String sql = "SELECT id, nombre_usuario, contrasena_hash FROM usuarios WHERE nombre_usuario = ? AND contrasena_hash = ?";
        Usuario usuarioLogueado = null;
        
        try (Connection con = Conexion.obtener(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenaHash);
            
            // Ejecutamos la consulta y guardamos el resultado en un ResultSet
            try (ResultSet rs = ps.executeQuery()) {
                // Si rs.next() es true, significa que encontró un usuario
                if (rs.next()) {
                    usuarioLogueado = new Usuario();
                    usuarioLogueado.setId(rs.getInt("id"));
                    usuarioLogueado.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuarioLogueado.setContrasenaHash(rs.getString("contrasena_hash"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
        }
        
        return usuarioLogueado;
    }
}