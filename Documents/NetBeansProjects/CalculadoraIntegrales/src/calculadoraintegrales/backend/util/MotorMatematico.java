/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadoraintegrales.backend.util;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MotorMatematico {

    /**
     * Calcula la integral definida usando el método numérico de Simpson 1/3.
     */
    public static double integrar(String funcion, String variable, double a, double b) throws Exception {
        int n = 10000; // Número de iteraciones
        if (n % 2 != 0) n++; // Simpson 1/3 requiere que sea par
        
        double h = (b - a) / n;
        
        Expression expr = new ExpressionBuilder(funcion)
                .variables(variable)
                .build();
        
        expr.setVariable(variable, a);
        double suma = expr.evaluate();
        
        expr.setVariable(variable, b);
        suma += expr.evaluate();
        
        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            expr.setVariable(variable, x);
            
            if (i % 2 == 0) {
                suma += 2 * expr.evaluate();
            } else {
                suma += 4 * expr.evaluate();
            }
        }
        
        return (h / 3.0) * suma;
    }
    
    // Calcula la derivada de cualquier función en un punto específico usando Diferencia Central
    public static double derivar(String funcion, String variable, double punto) throws Exception {
        net.objecthunter.exp4j.Expression e = new net.objecthunter.exp4j.ExpressionBuilder(funcion)
                .variables(variable)
                .build();

        double h = 1e-5; // Un paso microscópico para alta precisión

        // Calculamos f(x + h)
        e.setVariable(variable, punto + h);
        double f_mas_h = e.evaluate();

        // Calculamos f(x - h)
        e.setVariable(variable, punto - h);
        double f_menos_h = e.evaluate();

        // Aplicamos la fórmula de la Diferencia Central
        return (f_mas_h - f_menos_h) / (2 * h);
    }
}