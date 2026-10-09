package calculadoraintegrales.backend.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Universo7
 */
public class Polinomio {
    private double coeficiente;
    private double exponente;
    
    public Polinomio(double coeficiente, double exponente){
        this.coeficiente = coeficiente;
        this.exponente = exponente;
    }
    //Derivacion
    public Polinomio derivar() {
        
        if (this.exponente ==0) {
            return new Polinomio(0,0);
        }
        
        //(a*n)*x^(n-1)
        double nuevoCoeficiente= this.coeficiente * this.exponente;
        double nuevoExponente = this.exponente -1;
        
        return new Polinomio(nuevoCoeficiente, nuevoExponente);
       
    }
    //Integracion
    public Polinomio integrar() {
        if (this.exponente == -1) {
            throw new IllegalArgumentException("No se puede integrar x^-1 devolviendo un polinomio simple.");
        }
        
        //Regla: (a/(n+1))*x^(n+1)
        double nuevoExponente = this.exponente +1;
        double nuevoCoeficiente = this.coeficiente / nuevoExponente;
         
        return new Polinomio(nuevoCoeficiente, nuevoExponente);
    }
    
    //Getters
    //Geter coeficiente
    public double getCoeficiente(){
        return this.coeficiente;
    }
    
    //Gete Exponente
    public double getExponente(){
        return this.exponente;
    }
    
    //Metodo para calcular el valor numerico al sutituir 'x'
    public double evaluar(double valor){
        return this.coeficiente * Math.pow(valor, this.exponente);
    }
}
