package calculadoraintegrales;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcedimientoDAO {

    // Método 1: Guarda una nueva operación matemática en la base de datos
    public boolean guardarProcedimiento(Procedimiento proc) {
        String sql = "INSERT INTO procedimientos (id_usuario, funcion, variable, tipo, "
                   + "limite_inferior, limite_superior, resultado, procedimiento) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = Conexion.obtener(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, proc.getIdUsuario());
            ps.setString(2, proc.getFuncion());
            ps.setString(3, proc.getVariable());
            ps.setString(4, proc.getTipo());
            ps.setString(5, proc.getLimiteInferior());
            ps.setString(6, proc.getLimiteSuperior());
            ps.setString(7, proc.getResultado());
            ps.setString(8, proc.getProcedimientoPasoAPaso());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar el procedimiento: " + e.getMessage());
            return false;
        }
    }

    // Método 2: Obtiene todas las operaciones de un usuario específico (para el historial)
    public List<Procedimiento> obtenerHistorial(int idUsuario) {
        List<Procedimiento> lista = new ArrayList<>();
        // Ordenamos por fecha_hora de forma descendente (DESC) para ver los más recientes primero
        String sql = "SELECT * FROM procedimientos WHERE id_usuario = ? ORDER BY fecha_hora DESC";
        
        try (Connection con = Conexion.obtener(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Procedimiento proc = new Procedimiento(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getString("funcion"),
                        rs.getString("variable"),
                        rs.getString("tipo"),
                        rs.getString("limite_inferior"),
                        rs.getString("limite_superior"),
                        rs.getString("resultado"),
                        rs.getString("procedimiento"),
                        rs.getString("fecha_hora")
                    );
                    lista.add(proc);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener el historial: " + e.getMessage());
        }
        
        return lista;
    }

    // Método 3 (Opcional pero recomendado): Eliminar un registro del historial
public boolean eliminarProcedimiento(int idProcedimiento) {
        String sql = "DELETE FROM procedimientos WHERE id = ?";
        
        try (Connection con = Conexion.obtener(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProcedimiento);
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                return true; 
            } else {
                System.err.println("Atención: No se encontró el ID " + idProcedimiento + " en la tabla procedimientos.");
                return false; 
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el procedimiento: " + e.getMessage());
            return false;
        }
    }
}