package calculadoraintegrales;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Procedimiento {
    private int id;
    private int idUsuario;
    private String funcion;
    private String variable;
    private String tipo;
    private String limiteInferior;
    private String limiteSuperior;
    private String resultado;
    private String procedimientoPasoAPaso;
    private String fechaHora;

    // 1. Constructor vacío
    public Procedimiento() {
    }

    // 2. Constructor sin ID ni fecha (para cuando vamos a guardar uno nuevo en la base de datos)
    public Procedimiento(int idUsuario, String funcion, String variable, String tipo, 
                         String limiteInferior, String limiteSuperior, String resultado, 
                         String procedimientoPasoAPaso) {
        this.idUsuario = idUsuario;
        this.funcion = funcion;
        this.variable = variable;
        this.tipo = tipo;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.resultado = resultado;
        this.procedimientoPasoAPaso = procedimientoPasoAPaso;
    }

    // 3. Constructor completo (para cuando leemos el historial desde la base de datos)
    public Procedimiento(int id, int idUsuario, String funcion, String variable, String tipo, 
                         String limiteInferior, String limiteSuperior, String resultado, 
                         String procedimientoPasoAPaso, String fechaHora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.funcion = funcion;
        this.variable = variable;
        this.tipo = tipo;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.resultado = resultado;
        this.procedimientoPasoAPaso = procedimientoPasoAPaso;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(String limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public String getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(String limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getProcedimientoPasoAPaso() {
        return procedimientoPasoAPaso;
    }

    public void setProcedimientoPasoAPaso(String procedimientoPasoAPaso) {
        this.procedimientoPasoAPaso = procedimientoPasoAPaso;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

}
