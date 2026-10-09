package calculadoraintegrales.backend.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
import calculadoraintegrales.backend.model.Polinomio;
import java.util.List;
import java.util.ArrayList;

public class Parser {
    
    // 1. Modificamos la firma para recibir la variable
    private Polinomio parsearTermino(String termino, String variable) {
        double coeficiente;
        double exponente;

        // 2. Reemplazamos la "x" hardcodeada por el parámetro 'variable'
        if (!termino.contains(variable)) {
            // Es una constante
            coeficiente = Double.parseDouble(termino);
            exponente = 0;
        } else {
            // 3. Reemplazamos también en el split
            String[] partes = termino.split(variable);
            
            String parteCoef = partes.length > 0 ? partes[0] : ""; // antes de la variable

            if (parteCoef.isEmpty() || parteCoef.equals("+")) {
                coeficiente = 1;
            } else if (parteCoef.equals("-")) {
                coeficiente = -1;
            } else {
                coeficiente = Double.parseDouble(parteCoef);
            }

            if (termino.contains("^")) {
                String parteExp = termino.split("\\^")[1]; // después del ^
                exponente = Double.parseDouble(parteExp);
            } else {
                exponente = 1;
            }
        }

        return new Polinomio(coeficiente, exponente);
    }
    
    // 4. Modificamos la firma aquí también (y recuerda mantenerlo public)
    public List<Polinomio> parsearFuncion(String texto, String variable) {
        List<Polinomio> terminos = new ArrayList<>();
        // Eliminar espacios en blanco para uniformidad
        texto = texto.replace(" ", "");
        
        // Dividir manteniendo los signos + o - al inicio de cada término
        String[] partes = texto.split("(?=[+-])");
            
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                // 5. Pasamos la variable al llamar a parsearTermino
                Polinomio terminoParseado = parsearTermino(parte, variable);
                terminos.add(terminoParseado);
            }
        }
            
        return terminos;
    }
}