/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package calculadoraintegrales;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;
/**
 *
 * @author Usuario
 */
public class VentanaGrafica extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaGrafica.class.getName());

    private Usuario usuarioActual;
    private String funcionActiva;
    private String variableActiva;
    private double limInfActivo;
    private double limSupActivo;
    private String resultadoActivo;
    private PanelPlanoCartesiano lienzoMates;
    
    public VentanaGrafica(Usuario usuario, String funcion, String variable, String limInf, String limSup, String resultado) {
        initComponents();
        this.setLocationRelativeTo(null); 
        
        this.usuarioActual = usuario;
        this.funcionActiva = funcion;
        this.variableActiva = variable;
        
        try { this.limInfActivo = Double.parseDouble(limInf); } catch (Exception e) { this.limInfActivo = 0; }
        try { this.limSupActivo = Double.parseDouble(limSup); } catch (Exception e) { this.limSupActivo = 10; }
        
        this.resultadoActivo = (resultado != null && !resultado.isEmpty()) ? resultado : "0";
        
        // Cargar datos del usuario
        if (this.usuarioActual != null) {
            lblUsuario.setText(this.usuarioActual.getNombreUsuario());
            String inicial = (this.usuarioActual.getNombreUsuario() != null && !this.usuarioActual.getNombreUsuario().isEmpty())
                             ? this.usuarioActual.getNombreUsuario().substring(0, 1).toUpperCase() : "?";
            lblInicial.setText(inicial);
            lblInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }

        aplicarEstilosPersonalizados();
        actualizarDatosVisuales();
        
        // Inyectar el lienzo de dibujo
        pnlDibujo.setLayout(new java.awt.BorderLayout());
        lienzoMates = new PanelPlanoCartesiano();
        pnlDibujo.add(lienzoMates, java.awt.BorderLayout.CENTER);
        
        // Listeners para los checkboxes
        chkMostrarArea.addActionListener(e -> lienzoMates.repaint());
        chkMostrarCuadricula.addActionListener(e -> lienzoMates.repaint());
    }

    private void aplicarEstilosPersonalizados() {
   Color colorFondo = new Color(245, 247, 252);
        getContentPane().setBackground(colorFondo);
        
        pnlHeader.setBackground(colorFondo);
        pnlHeader.setOpaque(true);
        pnlSesion.setBackground(new Color(238, 241, 248));
        pnlSesion.setOpaque(true);
        
        pnlControles.setBackground(Color.WHITE);
        pnlControles.setOpaque(true);
        pnlLienzoGrafica.setBackground(Color.WHITE);
        pnlLienzoGrafica.setOpaque(true);
        pnlResumen.setBackground(Color.WHITE);
        pnlResumen.setOpaque(true);
        pnlDibujo.setBackground(Color.WHITE);
        pnlDibujo.setOpaque(true);
        
        pnlControles.putClientProperty("JComponent.arc", 16);
        pnlLienzoGrafica.putClientProperty("JComponent.arc", 16);
        pnlResumen.putClientProperty("JComponent.arc", 16);
        
        Color colorAzulOscuro = new Color(29, 42, 82);
        lblIntegraHeader.setForeground(colorAzulOscuro);
        lblTituloApp.setForeground(colorAzulOscuro);
        lblTituloApp.setBackground(colorFondo); 
        lblUsuario.setForeground(colorAzulOscuro);
        lblLogo2.setForeground(new Color(153, 153, 153));
        lblLogoHeader.setForeground(new Color(0, 51, 204));
        
        Color colorTextoNegro = new Color(20, 20, 20); 
        lblTituloGrafica.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTituloGrafica.setForeground(colorTextoNegro);
        lblEcuacionGrafica.setForeground(colorTextoNegro);
        lblTituloResumen.setForeground(colorTextoNegro);
        lblDetalleIntervalo.setForeground(colorTextoNegro);
        jLabel3.setForeground(colorTextoNegro);
        lbltxtLimInf.setForeground(colorTextoNegro);
        lblValLimInf.setForeground(colorTextoNegro);
        lbltxtLimSup.setForeground(colorTextoNegro);
        lblValLimSup.setForeground(colorTextoNegro);
        lblTxtVar1.setForeground(colorTextoNegro);
        lblVarVar.setForeground(colorTextoNegro);
        
        lblResultadoG.setFont(new java.awt.Font("Cambria Math", java.awt.Font.BOLD, 36));
        lblResultadoG.setForeground(colorTextoNegro);
        lblResultadoDec.setForeground(new Color(48, 113, 196)); 
        lblResultadoDec.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        
        lblLeyendaFx.setForeground(new Color(30, 90, 200));
        lblLeyendaFx.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblLeyendaFx.setText("━ f(" + variableActiva + ")");
        
        lblLeyendaArea.setForeground(new Color(173, 216, 230));
        lblLeyendaArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblLeyendaArea.setText("⬛ Área integrada");
        
        // ===== ESTILO DEL AVATAR (INICIAL DEL USUARIO) =====
        // 1. Forzamos a que el fondo deje de ser transparente
        lblInicial.setOpaque(true);
        
        // 2. Le damos un fondo azul fuerte y texto blanco
        lblInicial.setBackground(new Color(48, 113, 196)); 
        lblInicial.setForeground(Color.WHITE); 
        
        // 3. Tipografía en negrita y más grande
        lblInicial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        
        // 4. Centramos la letra perfectamente
        lblInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // 5. Magia de FlatLaf para hacerlo completamente redondo (círculo)
        lblInicial.putClientProperty("FlatLaf.style", "arc: 999");
        
        // Opcional: Asegurarnos de que el nombre del usuario también resalte
        lblUsuario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblUsuario.setForeground(new Color(29, 42, 82));
        

        javax.swing.JTextField[] campos = {txtFuncion1, txtVariable, lblLimInf, jTextField1};
        for (javax.swing.JTextField campo : campos) {
            campo.setOpaque(true); 
            campo.setBackground(Color.WHITE);
            campo.setForeground(colorTextoNegro);
            campo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            campo.putClientProperty("FlatLaf.style", "arc:14; borderColor:#3071C4; focusedBorderColor:#0055FF; focusWidth:1");
            campo.setMargin(new java.awt.Insets(6, 12, 6, 12));
        }

        // ===== FIX DEL ERROR: SE ELIMINÓ iconArc =====
        javax.swing.JCheckBox[] checks = {chkMostrarArea, chkMostrarCuadricula};
        for (javax.swing.JCheckBox chk : checks) {
            chk.setOpaque(false); 
            chk.setForeground(colorTextoNegro);
            chk.putClientProperty("FlatLaf.style", "iconTextGap:8; iconBorderColor:#3071C4; iconBackground:#FFFFFF; checkmarkColor:#3071C4; iconFocusedBorderColor:#0055FF");
        }

        btnActualizar.setBackground(new Color(48, 113, 196));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnActualizar.putClientProperty("JComponent.arc", 10);
        
        javax.swing.border.Border bordeRedondo = new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 205, 215)); 
                g2.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
                g2.dispose();
            }
            @Override
            public java.awt.Insets getBorderInsets(java.awt.Component c) {
                return new java.awt.Insets(6, 12, 6, 12);
            }
        };

        btnVolver.setBackground(Color.WHITE);
        btnVolver.setForeground(new Color(48, 113, 196));
        btnVolver.setBorder(bordeRedondo);
        btnVolver.setFocusPainted(false);
        
        BtnHistorial.setBackground(new Color(232, 242, 255));
        BtnHistorial.setForeground(new Color(30, 90, 200));   
        BtnHistorial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        BtnHistorial.setBorder(bordeRedondo);
        BtnHistorial.setFocusPainted(false);
        BtnHistorial.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }
    
  private void actualizarDatosVisuales() {
        txtFuncion1.setText(this.funcionActiva);
        txtVariable.setText(this.variableActiva);
        lblLimInf.setText(String.valueOf(this.limInfActivo));      
        jTextField1.setText(String.valueOf(this.limSupActivo));    
        
        lblEcuacionGrafica.setText("f(" + this.variableActiva + ") = " + this.funcionActiva);
        lblDetalleIntervalo.setText("Integral definida en [" + this.limInfActivo + ", " + this.limSupActivo + "]");
        
        // ===== LA SOLUCIÓN =====
        // Limpiamos el HTML y le pedimos explícitamente que traduzca el código &#8776; a ≈
        String resLimpio = this.resultadoActivo.replaceAll("<[^>]*>", "").replace("&#8776;", "≈").trim();
        
        if(resLimpio.contains("≈")) {
            String[] partes = resLimpio.split("≈");
            lblResultadoG.setText(partes[0].trim()); // Fracción
            lblResultadoDec.setText("≈ " + partes[1].trim()); // Decimal
        } else {
            lblResultadoG.setText(resLimpio);
            lblResultadoDec.setText("");
        }
        
        lblValLimInf.setText(String.valueOf(this.limInfActivo));
        lblValLimSup.setText(String.valueOf(this.limSupActivo));
        lblVarVar.setText(this.variableActiva);
    }
  
  // ===== MÉTODOS AUXILIARES PARA RECALCULAR RESULTADOS =====
    private String formatearNumero(double numero) {
        java.text.DecimalFormat formato = new java.text.DecimalFormat("#.##");
        formato.setDecimalFormatSymbols(java.text.DecimalFormatSymbols.getInstance(java.util.Locale.US));
        return formato.format(numero);
    }

    private long mcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a == 0 ? 1 : a;
    }

    private String doubleToFraccion(double valor) {
        if (valor == 0) return "0";
        int signo = valor < 0 ? -1 : 1;
        valor = Math.abs(valor);

        long mejorNum = Math.round(valor);
        long mejorDen = 1;
        double mejorError = Math.abs(valor - mejorNum);

        for (long den = 1; den <= 10000; den++) {
            long num = Math.round(valor * den);
            double error = Math.abs(valor - (double) num / den);
            if (error < mejorError - 1e-9) {
                mejorError = error;
                mejorNum = num;
                mejorDen = den;
            }
            if (error < 1e-9) break;
        }

        long divisor = mcd(mejorNum, mejorDen);
        mejorNum /= divisor;
        mejorDen /= divisor;

        if (mejorDen == 1) return String.valueOf(signo * mejorNum);
        return (signo < 0 ? "-" : "") + mejorNum + "/" + mejorDen;
    }

    // =========================================================================
    // CLASE INTERNA: MOTOR GRÁFICO DEL PLANO CARTESIANO
    // =========================================================================
   
   class PanelPlanoCartesiano extends JPanel {
        
        public PanelPlanoCartesiano() {
            setOpaque(true); 
            setBackground(new Color(248, 250, 252)); // Gris super claro, como en la imagen
        }

        private double calcularPasoAmigable(double rango, int maxLineas) {
            if (rango <= 0) return 1;
            double pasoBruto = rango / maxLineas;
            double magnitud = Math.pow(10, Math.floor(Math.log10(pasoBruto)));
            double residuo = pasoBruto / magnitud;
            
            if (residuo > 5) return 10 * magnitud;
            if (residuo > 2) return 5 * magnitud;
            if (residuo > 1) return 2 * magnitud;
            return magnitud;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Fondo principal del panel
            g2.setColor(new Color(248, 250, 252));
            g2.fillRect(0, 0, getWidth(), getHeight());

            Parser parser = new Parser();
            List<Polinomio> terminos;
            try { terminos = parser.parsearFuncion(funcionActiva, variableActiva); } 
            catch (Exception e) { return; }

            // Calcular rangos
            double spanX = limSupActivo - limInfActivo;
            if (spanX <= 0) spanX = 10;
            double xMin = limInfActivo - spanX * 0.05; // Ajuste pegado a la izquierda como la imagen
            double xMax = limSupActivo + spanX * 0.15; // Espacio a la derecha
            if (limInfActivo == 0) xMin = -spanX * 0.05;

            double yMin = 0, yMax = 0;
            for (double x = xMin; x <= xMax; x += spanX / 500.0) {
                double y = evaluarLista(terminos, x);
                if (y < yMin) yMin = y;
                if (y > yMax) yMax = y;
            }
            
            double spanY = yMax - yMin;
            if (spanY == 0) { yMax += 5; yMin -= 5; } 
            else { yMax += spanY * 0.1; }

            // Configurar márgenes de dibujo (Caja principal)
            int padLeft = 55;   // Espacio para números Y y texto "f(x)"
            int padBottom = 30; // Espacio para números X
            int padTop = 20;
            int padRight = 20;
            
            int width = getWidth() - padLeft - padRight;
            int height = getHeight() - padTop - padBottom;
            if (width <= 0 || height <= 0) return;

            double scaleX = width / (xMax - xMin);
            double scaleY = height / (yMax - yMin);
            
            int drawOrigY = padTop + (int) (yMax * scaleY); // Y pixel for Y=0
            int drawOrigX = padLeft + (int) (-xMin * scaleX); // X pixel for X=0
            drawOrigY = Math.max(padTop, Math.min(padTop + height, drawOrigY));
            drawOrigX = Math.max(padLeft, Math.min(padLeft + width, drawOrigX));

            // ==== DIBUJAR CUADRÍCULA ====
            java.text.DecimalFormat dfX = new java.text.DecimalFormat("0.0"); // Formato 0.5, 1.0...
            java.text.DecimalFormat dfY = new java.text.DecimalFormat("#.##"); // Formato 100, 200...
            g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

            double stepX = calcularPasoAmigable(xMax - xMin, 10);
            double stepY = calcularPasoAmigable(yMax - yMin, 8);

            if (chkMostrarCuadricula.isSelected()) {
                g2.setStroke(new BasicStroke(1.0f));
                
                // Líneas Horizontales (Eje Y)
                for (double i = Math.ceil(yMin / stepY) * stepY; i <= yMax; i += stepY) {
                    if (Math.abs(i) < 1e-9) i = 0;
                    int py = padTop + (int) ((yMax - i) * scaleY);
                    
                    g2.setColor(new Color(230, 235, 240)); // Línea gris claro
                    g2.drawLine(padLeft, py, getWidth() - padRight, py); 
                    
                    g2.setColor(new Color(100, 110, 120)); // Número oscuro
                    if (i != 0 || yMin >= 0) { // Mostrar el 0 si es la base
                        String num = dfY.format(i);
                        int sw = g2.getFontMetrics().stringWidth(num);
                        g2.drawString(num, padLeft - sw - 8, py + 4);
                    }
                }
                
                // Líneas Verticales (Eje X)
                for (double i = Math.ceil(xMin / stepX) * stepX; i <= xMax; i += stepX) {
                    if (Math.abs(i) < 1e-9) i = 0; 
                    int px = padLeft + (int) ((i - xMin) * scaleX);
                    
                    g2.setColor(new Color(230, 235, 240));
                    g2.drawLine(px, padTop, px, getHeight() - padBottom); 
                    
                    g2.setColor(new Color(100, 110, 120));
                    String num = dfX.format(i);
                    int sw = g2.getFontMetrics().stringWidth(num);
                    g2.drawString(num, px - sw / 2, getHeight() - padBottom + 18);
                }
            }

            // ==== ETIQUETA ROTADA "f(x)" ====
            Graphics2D gLabel = (Graphics2D) g2.create();
            gLabel.setColor(new Color(100, 110, 120));
            gLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            gLabel.translate(18, padTop + height / 2);
            gLabel.rotate(-Math.PI / 2);
            int textW = gLabel.getFontMetrics().stringWidth("f(x)");
            gLabel.drawString("f(x)", -textW / 2, 0);
            gLabel.dispose();

            // Bordes Principales de los Ejes
            g2.setColor(new Color(200, 205, 215));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(padLeft, padTop, padLeft, getHeight() - padBottom); // Eje Y (Izquierdo)
            g2.drawLine(padLeft, drawOrigY, getWidth() - padRight, drawOrigY); // Eje X (Abajo)

            // ==== ÁREA INTEGRADA ====
            int pxLimInf = padLeft + (int) ((limInfActivo - xMin) * scaleX);
            int pxLimSup = padLeft + (int) ((limSupActivo - xMin) * scaleX);
            
            if (chkMostrarArea.isSelected()) {
                java.awt.geom.GeneralPath areaPath = new java.awt.geom.GeneralPath();
                areaPath.moveTo(pxLimInf, drawOrigY);
                for (double x = limInfActivo; x <= limSupActivo; x += spanX / 200.0) {
                    double y = evaluarLista(terminos, x);
                    int px = padLeft + (int) ((x - xMin) * scaleX);
                    int py = padTop + (int) ((yMax - y) * scaleY);
                    areaPath.lineTo(px, py);
                }
                areaPath.lineTo(pxLimSup, drawOrigY);
                areaPath.closePath();
                
                g2.setColor(new Color(66, 133, 244, 70)); // Azul semitransparente como la imagen
                g2.fill(areaPath);
                
                // Línea Punteada Gris Oscura en el Límite Superior
                float[] dash = {6.0f, 4.0f};
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash, 0.0f));
                g2.setColor(new Color(120, 120, 120)); // Gris oscuro punteado
                int pyLimSup = padTop + (int)((yMax - evaluarLista(terminos, limSupActivo)) * scaleY);
                g2.drawLine(pxLimSup, drawOrigY, pxLimSup, pyLimSup);
                
                // Etiqueta superior "x = 5"
                g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                String lblLim = variableActiva + " = " + dfX.format(limSupActivo);
                int swLim = g2.getFontMetrics().stringWidth(lblLim);
                g2.drawString(lblLim, pxLimSup - swLim / 2, pyLimSup - 8);

                // Etiqueta Central "Área = 781.25"
                g2.setColor(new Color(60, 70, 80)); // Gris Oscuro Fuerte
                g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
                String resTexto = lblResultadoDec.getText().replace("≈", "").trim();
                if(resTexto.isEmpty()) resTexto = lblResultadoG.getText();
                String txtAreaCentrada = "Área = " + resTexto;
                
                int swArea = g2.getFontMetrics().stringWidth(txtAreaCentrada);
                int pxCenter = padLeft + (int) ((((limInfActivo + limSupActivo) / 2) - xMin) * scaleX);
                int pyCenter = drawOrigY - 15; // Pegadito al eje X
                
                // Si el área es muy chiquita, se dibuja un poco más arriba
                if (padTop + (int)((yMax - evaluarLista(terminos, (limInfActivo + limSupActivo) / 2)) * scaleY) > pyCenter - 20) {
                    pyCenter = drawOrigY - 30; 
                }
                g2.drawString(txtAreaCentrada, pxCenter - swArea / 2, pyCenter);
            }

            // ==== CURVA PRINCIPAL ====
            g2.setColor(new Color(66, 133, 244)); // Azul fuerte Google
            g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); 
            
            java.awt.geom.GeneralPath curvaPath = new java.awt.geom.GeneralPath();
            boolean first = true;
            for (int px = padLeft; px <= getWidth() - padRight; px++) {
                double x = xMin + ((px - padLeft) / scaleX);
                double y = evaluarLista(terminos, x);
                int py = padTop + (int) ((yMax - y) * scaleY);
                
                if (py < -1000) py = -1000;
                if (py > height + 1000) py = height + 1000;
                
                if (first) {
                    curvaPath.moveTo(px, py);
                    first = false;
                } else {
                    curvaPath.lineTo(px, py);
                }
            }
            g2.draw(curvaPath);
        }
        
        private double evaluarLista(List<Polinomio> terminos, double x) {
            double total = 0;
            for(Polinomio p : terminos) {
                total += p.evaluar(x);
            }
            return total;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblLogoHeader = new javax.swing.JLabel();
        lblIntegraHeader = new javax.swing.JLabel();
        lblLogo2 = new javax.swing.JLabel();
        lblTituloApp = new javax.swing.JLabel();
        pnlSesion = new javax.swing.JPanel();
        lblInicial = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        pnlControles = new javax.swing.JPanel();
        lblFuncion = new javax.swing.JLabel();
        txtVariable = new javax.swing.JTextField();
        lblVariable = new javax.swing.JLabel();
        txtFuncion1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblLimInf = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        pnlLienzoGrafica = new javax.swing.JPanel();
        lblTituloGrafica = new javax.swing.JLabel();
        lblEcuacionGrafica = new javax.swing.JLabel();
        lblLeyendaFx = new javax.swing.JLabel();
        lblLeyendaArea = new javax.swing.JLabel();
        pnlDibujo = new javax.swing.JPanel();
        pnlResumen = new javax.swing.JPanel();
        lblTituloResumen = new javax.swing.JLabel();
        lblDetalleIntervalo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        lblResultadoG = new javax.swing.JLabel();
        lblResultadoDec = new javax.swing.JLabel();
        lbltxtLimInf = new javax.swing.JLabel();
        lblValLimInf = new javax.swing.JLabel();
        lbltxtLimSup = new javax.swing.JLabel();
        lblValLimSup = new javax.swing.JLabel();
        lblVarVar = new javax.swing.JLabel();
        lblTxtVar1 = new javax.swing.JLabel();
        chkMostrarArea = new javax.swing.JCheckBox();
        chkMostrarCuadricula = new javax.swing.JCheckBox();
        btnVolver = new javax.swing.JButton();
        BtnHistorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(245, 247, 252));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoHeader.setFont(new java.awt.Font("Cambria Math", 1, 24)); // NOI18N
        lblLogoHeader.setForeground(new java.awt.Color(0, 51, 204));
        lblLogoHeader.setText("∫");
        pnlHeader.add(lblLogoHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 7, 40, 40));

        lblIntegraHeader.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblIntegraHeader.setText("INTEGRA 7");
        pnlHeader.add(lblIntegraHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        lblLogo2.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblLogo2.setForeground(new java.awt.Color(153, 153, 153));
        lblLogo2.setText("UNIVERSO7");
        pnlHeader.add(lblLogo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        lblTituloApp.setBackground(new java.awt.Color(245, 247, 252));
        lblTituloApp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloApp.setText("Calculadora de integrales");
        lblTituloApp.setToolTipText("");
        lblTituloApp.setOpaque(true);
        pnlHeader.add(lblTituloApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 17, -1, -1));

        pnlSesion.setBackground(new java.awt.Color(238, 241, 248));
        pnlSesion.setPreferredSize(new java.awt.Dimension(115, 38));
        pnlSesion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInicial.setBackground(new java.awt.Color(0, 0, 255));
        lblInicial.setForeground(new java.awt.Color(51, 102, 255));
        lblInicial.setPreferredSize(new java.awt.Dimension(28, 28));
        pnlSesion.add(lblInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 20, 20));

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsuario.setText("jLabel7");
        pnlSesion.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 18, -1, -1));

        pnlHeader.add(pnlSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 140, -1));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 55));

        pnlControles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFuncion.setText("FUNCION");
        pnlControles.add(lblFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        txtVariable.addActionListener(this::txtVariableActionPerformed);
        pnlControles.add(txtVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 35, 100, 35));

        lblVariable.setText("VARIABLE");
        pnlControles.add(lblVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 15, -1, -1));

        txtFuncion1.addActionListener(this::txtFuncion1ActionPerformed);
        pnlControles.add(txtFuncion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 340, 35));

        jLabel1.setText("LIMITE INFERIOR");
        pnlControles.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 15, -1, -1));
        pnlControles.add(lblLimInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 35, 130, 35));

        jLabel2.setText("LIMITE SUPERIOR");
        pnlControles.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 15, -1, -1));
        pnlControles.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 35, 130, 35));

        btnActualizar.setText("Actualizar grafica");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        pnlControles.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 35, 200, 35));

        getContentPane().add(pnlControles, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 1040, 85));

        pnlLienzoGrafica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloGrafica.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloGrafica.setText("Area bajo la curva");
        pnlLienzoGrafica.add(lblTituloGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 15, -1, -1));

        lblEcuacionGrafica.setText("jLabel3");
        pnlLienzoGrafica.add(lblEcuacionGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 35, -1, -1));

        lblLeyendaFx.setText("jLabel3");
        pnlLienzoGrafica.add(lblLeyendaFx, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

        lblLeyendaArea.setText("jLabel3");
        pnlLienzoGrafica.add(lblLeyendaArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, -1));

        javax.swing.GroupLayout pnlDibujoLayout = new javax.swing.GroupLayout(pnlDibujo);
        pnlDibujo.setLayout(pnlDibujoLayout);
        pnlDibujoLayout.setHorizontalGroup(
            pnlDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        pnlDibujoLayout.setVerticalGroup(
            pnlDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        pnlLienzoGrafica.add(pnlDibujo, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 60, 650, 250));

        getContentPane().add(pnlLienzoGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 185, 700, 280));

        pnlResumen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTituloResumen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTituloResumen.setText("Resumen");
        pnlResumen.add(lblTituloResumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, -1, -1));

        lblDetalleIntervalo.setText("jLabel3");
        pnlResumen.add(lblDetalleIntervalo, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 35, -1, -1));
        pnlResumen.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 55, 270, 10));
        pnlResumen.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 210, 270, 10));

        jLabel3.setText("Valor de la integral");
        pnlResumen.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 65, -1, -1));

        lblResultadoG.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblResultadoG.setText("jLabel4");
        pnlResumen.add(lblResultadoG, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 85, 170, 40));

        lblResultadoDec.setText("jLabel4");
        pnlResumen.add(lblResultadoDec, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 100, 25));

        lbltxtLimInf.setText("Limite inferior");
        pnlResumen.add(lbltxtLimInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 135, -1, -1));

        lblValLimInf.setText("jLabel4");
        pnlResumen.add(lblValLimInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 135, -1, -1));

        lbltxtLimSup.setText("Limite superior");
        pnlResumen.add(lbltxtLimSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 160, -1, -1));

        lblValLimSup.setText("jLabel4");
        pnlResumen.add(lblValLimSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 160, -1, -1));

        lblVarVar.setText("jLabel4");
        pnlResumen.add(lblVarVar, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 185, -1, -1));

        lblTxtVar1.setText("Variable");
        pnlResumen.add(lblTxtVar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 185, -1, -1));

        chkMostrarArea.setText("Mostrar area integrada");
        chkMostrarArea.addActionListener(this::chkMostrarAreaActionPerformed);
        pnlResumen.add(chkMostrarArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 225, -1, -1));

        chkMostrarCuadricula.setText("Mostrar cuadricula");
        chkMostrarCuadricula.addActionListener(this::chkMostrarCuadriculaActionPerformed);
        pnlResumen.add(chkMostrarCuadricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 250, -1, -1));

        getContentPane().add(pnlResumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 185, 320, 310));

        btnVolver.setText("Volver a la Calculadora");
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, -1));

        BtnHistorial.setText("Historial");
        BtnHistorial.addActionListener(this::BtnHistorialActionPerformed);
        getContentPane().add(BtnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 520, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        new VentanaCalculadora(this.usuarioActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVariableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVariableActionPerformed

    private void txtFuncion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFuncion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFuncion1ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String textoFuncion = txtFuncion1.getText().trim();
        String txtVar = txtVariable.getText().trim();
        
        try {
            double limInf = Double.parseDouble(lblLimInf.getText().trim());
            double limSup = Double.parseDouble(jTextField1.getText().trim());
            
            // 1. Recalcular la integral matemáticamente
            Parser parser = new Parser();
            List<Polinomio> terminos = parser.parsearFuncion(textoFuncion, txtVar);
            
            double resultadoAcumulado = 0;
            for (Polinomio termino : terminos) {
                Polinomio terminoIntegrado = termino.integrar();
                double evalSup = terminoIntegrado.evaluar(limSup);
                double evalInf = terminoIntegrado.evaluar(limInf);
                resultadoAcumulado += (evalSup - evalInf);
            }
            
            // 2. Actualizar las variables de la ventana
            this.funcionActiva = textoFuncion;
            this.variableActiva = txtVar;
            this.limInfActivo = limInf;
            this.limSupActivo = limSup;
            
            // 3. Formatear el nuevo resultado (Fracción y Decimal)
            String decimalStr = formatearNumero(resultadoAcumulado);
            String fraccionStr = doubleToFraccion(resultadoAcumulado);
            
            if (fraccionStr.equals(decimalStr)) {
                this.resultadoActivo = fraccionStr;
            } else {
                this.resultadoActivo = fraccionStr + " ≈ " + decimalStr;
            }
            
            // 4. Mandar a redibujar textos y lienzo visual
            actualizarDatosVisuales();
            lienzoMates.repaint();
            
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Los límites deben ser numéricos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error de sintaxis en la función.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void BtnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHistorialActionPerformed
        if (this.usuarioActual != null) {
            new VentanaHistorial(this.usuarioActual).setVisible(true);
            this.dispose(); 
        }
    }//GEN-LAST:event_BtnHistorialActionPerformed

    private void chkMostrarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMostrarAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMostrarAreaActionPerformed

    private void chkMostrarCuadriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMostrarCuadriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMostrarCuadriculaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Corregido: Pasamos null al constructor temporal de main para evitar errores de compilación
        java.awt.EventQueue.invokeLater(() -> new VentanaGrafica(null, "x^2", "x", "0", "5", "0").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHistorial;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkMostrarArea;
    private javax.swing.JCheckBox chkMostrarCuadricula;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDetalleIntervalo;
    private javax.swing.JLabel lblEcuacionGrafica;
    private javax.swing.JLabel lblFuncion;
    private javax.swing.JLabel lblInicial;
    private javax.swing.JLabel lblIntegraHeader;
    private javax.swing.JLabel lblLeyendaArea;
    private javax.swing.JLabel lblLeyendaFx;
    private javax.swing.JTextField lblLimInf;
    private javax.swing.JLabel lblLogo2;
    private javax.swing.JLabel lblLogoHeader;
    private javax.swing.JLabel lblResultadoDec;
    private javax.swing.JLabel lblResultadoG;
    private javax.swing.JLabel lblTituloApp;
    private javax.swing.JLabel lblTituloGrafica;
    private javax.swing.JLabel lblTituloResumen;
    private javax.swing.JLabel lblTxtVar1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblValLimInf;
    private javax.swing.JLabel lblValLimSup;
    private javax.swing.JLabel lblVarVar;
    private javax.swing.JLabel lblVariable;
    private javax.swing.JLabel lbltxtLimInf;
    private javax.swing.JLabel lbltxtLimSup;
    private javax.swing.JPanel pnlControles;
    private javax.swing.JPanel pnlDibujo;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlLienzoGrafica;
    private javax.swing.JPanel pnlResumen;
    private javax.swing.JPanel pnlSesion;
    private javax.swing.JTextField txtFuncion1;
    private javax.swing.JTextField txtVariable;
    // End of variables declaration//GEN-END:variables
}
