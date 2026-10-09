package calculadoraintegrales;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguridad {

    // Convierte una contraseña en texto a un hash SHA-256 (hexadecimal)
    public static String hashearContrasena(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // UTF-8 explícito: el hash es igual en cualquier computadora
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convertimos los bytes a formato hexadecimal (texto legible)
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña: " + e.getMessage());
        }
    }
}