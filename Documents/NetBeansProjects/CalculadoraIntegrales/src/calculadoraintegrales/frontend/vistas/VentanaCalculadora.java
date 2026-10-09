/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Usuario
 */
package calculadoraintegrales.frontend.vistas;

import calculadoraintegrales.backend.dao.ProcedimientoDAO;
import calculadoraintegrales.backend.util.Parser;
import calculadoraintegrales.backend.model.Polinomio;
import calculadoraintegrales.backend.model.Procedimiento;
import calculadoraintegrales.backend.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class VentanaCalculadora extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaCalculadora.class.getName());

    // Atributo para guardar el usuario que inició sesión
    private Usuario usuarioActual;

    public VentanaCalculadora(Usuario usuario) {
        initComponents();
        this.usuarioActual = usuario;

        if (this.usuarioActual != null) {
            lblUsuario.setText("Sesion: " + this.usuarioActual.getNombreUsuario());
        }

        // ==== Colores Header ====
        lblIntegraHeader.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        lblLogo2.setForeground(new java.awt.Color(0x66, 0x73, 0x90));
        lblTituloApp.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        lblUsuario.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        pnlSesion.setBounds(930, 8, 170, 38);
        lblUsuario.setBounds(40, 12, 120, 16);

        // ===== Avatar con inicial =====
        String inicial = (usuario != null && usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty())
                ? usuario.getNombreUsuario().substring(0, 1).toUpperCase() : "?";
        lblInicial.setOpaque(true);
        lblInicial.setBackground(new java.awt.Color(0x30, 0x71, 0xC4));
        lblInicial.setForeground(java.awt.Color.WHITE);
        lblInicial.setText(inicial);
        lblInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInicial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblInicial.setBounds(8, 8, 26, 26);
        lblInicial.putClientProperty("JComponent.arc", 26);

        // ===== REDISEÑO DEL SCROLL PARA PASOS =====
        PanelPasos.removeAll();
        PanelPasos.setLayout(new java.awt.BorderLayout());

        pnlPasosContenedor.setLayout(new javax.swing.BoxLayout(pnlPasosContenedor, javax.swing.BoxLayout.Y_AXIS));
        pnlPasosContenedor.setBackground(java.awt.Color.WHITE);

        javax.swing.JScrollPane scrollPasos = new javax.swing.JScrollPane(pnlPasosContenedor);
        scrollPasos.setBorder(null);
        scrollPasos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPasos.getVerticalScrollBar().setUnitIncrement(16);

        PanelPasos.add(scrollPasos, java.awt.BorderLayout.CENTER);
        PanelPasos.revalidate();
        PanelPasos.repaint();

        // ===== Personalización de Paneles y Textos =====
        getContentPane().setBackground(new java.awt.Color(0xF5, 0xF7, 0xFC));
        pnlResultado.putClientProperty("JComponent.arc", 16);
        pnlProcedimiento.putClientProperty("JComponent.arc", 16);
        pnlConfig.putClientProperty("JComponent.arc", 16);

        lblTipoOperacion.setOpaque(true);
        lblTipoOperacion.setBackground(new java.awt.Color(0xE8, 0xF2, 0xFF));
        lblTipoOperacion.putClientProperty("JComponent.arc", 12);

        lblConfigtitulo.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        jLabel1.setForeground(new java.awt.Color(0x66, 0x73, 0x90));

        // ===== 1. CREACIÓN DEL BORDE REDONDEADO MAESTRO =====
        javax.swing.border.Border bordeRedondo = new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(200, 205, 215)); // Color gris azulado sutil
                g2.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
                g2.dispose();
            }

            @Override
            public java.awt.Insets getBorderInsets(java.awt.Component c) {
                return new java.awt.Insets(6, 12, 6, 12); // Márgenes internos (padding)
            }
        };

        // ===== 2. APLICACIÓN A CAMPOS DE TEXTO (Los rebeldes) =====
        txtFuncion.setOpaque(false);
        txtLimiteInferior.setOpaque(false);
        txtLimiteSuperior.setOpaque(false);
        cbxVariable.setBackground(java.awt.Color.WHITE);
        cbxVariable.setOpaque(true);

        txtFuncion.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        txtLimiteInferior.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        txtLimiteSuperior.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        cbxVariable.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));

        txtFuncion.setBorder(bordeRedondo);
        txtLimiteInferior.setBorder(bordeRedondo);
        txtLimiteSuperior.setBorder(bordeRedondo);
        cbxVariable.setBorder(bordeRedondo);

        // ===== 3. BOTONES DE ACCIÓN (Derivar / Integrar) =====
        btnDerivar.setBackground(new java.awt.Color(0xEA, 0xF2, 0xFF));
        btnDerivar.setForeground(new java.awt.Color(0x30, 0x71, 0xC4));
        btnDerivar.setOpaque(true);
        btnDerivar.setBorderPainted(false);
        btnDerivar.setFocusPainted(false);
        btnDerivar.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnDerivar.putClientProperty("JComponent.arc", 10);

        btnIntegrar.setOpaque(true);
        btnIntegrar.setBorderPainted(false);
        btnIntegrar.setFocusPainted(false);
        btnIntegrar.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnIntegrar.putClientProperty("JComponent.arc", 10);

        // ===== 4. BOTONES INFERIORES (Historial / Salir) =====
        btnHistorial.setText("Ver Historial");
        btnHistorial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnHistorial.setBackground(java.awt.Color.WHITE);
        btnHistorial.setForeground(new java.awt.Color(29, 42, 82)); // Azul oscuro
        btnHistorial.setBorder(bordeRedondo);
        btnHistorial.setFocusPainted(false);
        btnHistorial.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorial.setBounds(20, 530, 150, 32);

        btnVerGrafica.setText("Ver Grafica");
        btnVerGrafica.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnVerGrafica.setBackground(java.awt.Color.WHITE);
        btnVerGrafica.setForeground(new java.awt.Color(194, 186, 37)); 
        btnVerGrafica.setBorder(bordeRedondo);
        btnVerGrafica.setFocusPainted(false);
        btnVerGrafica.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnVerGrafica.setBounds(1010, 530, 80, 32);
        
        btnCerrar.setText("Cerrar Sesion");
        btnCerrar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnCerrar.setBackground(java.awt.Color.WHITE);
        btnCerrar.setForeground(new java.awt.Color(37, 82, 194)); // Azul oscuro
        btnCerrar.setBorder(bordeRedondo);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.setBounds(20, 530, 150, 32);

        // ===== 5. CORRECCIÓN DEL FONDO OSCURO DEL COMBOBOX =====
        cbxVariable.putClientProperty("JComboBox.buttonBackground", java.awt.Color.WHITE);
        cbxVariable.putClientProperty("JComboBox.buttonHoverBackground", new java.awt.Color(240, 245, 255));
        cbxVariable.putClientProperty("JComboBox.buttonPressedBackground", new java.awt.Color(232, 242, 255));

        cbxVariable.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                ((javax.swing.JComponent) c).setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12));

                if (isSelected) {
                    c.setBackground(new java.awt.Color(232, 242, 255));
                    c.setForeground(new java.awt.Color(48, 113, 196));
                } else {
                    c.setBackground(java.awt.Color.WHITE);
                    c.setForeground(new java.awt.Color(29, 42, 82));
                }
                return c;
            }
        });

        // ===== NAVEGACIÓN CON TECLADO (Calculadora) =====
        // 1. Desde Función a Límite Inferior
        txtFuncion.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    txtLimiteInferior.requestFocusInWindow();
                }
            }
        });

        // 2. Desde Límite Inferior a Límite Superior (o regresar a Función)
        txtLimiteInferior.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    txtLimiteSuperior.requestFocusInWindow();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    txtFuncion.requestFocusInWindow();
                }
            }
        });

        // 3. Desde Límite Superior: Integrar automáticamente (Enter) o regresar a Límite Inferior (Flecha Arriba)
        txtLimiteSuperior.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnIntegrar.doClick(); // Simula un clic en el botón Integrar
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    txtLimiteInferior.requestFocusInWindow();
                }
            }
        });

        // 4. Poner el cursor automáticamente en "Función" al abrir la calculadora
        javax.swing.SwingUtilities.invokeLater(() -> txtFuncion.requestFocusInWindow());
    }

    public VentanaCalculadora() {
        initComponents();
    }
    
    

    private void mostrarPasosVisual(List<String[]> pasos) {
        pnlPasosContenedor.removeAll();
        int anchoDisponible = 440; // ancho útil dentro del scroll

        for (int i = 0; i < pasos.size(); i++) {
            String[] paso = pasos.get(i);

            // 1. Panel para este paso específico
            JPanel pnlPaso = new JPanel();
            pnlPaso.setLayout(new BoxLayout(pnlPaso, BoxLayout.Y_AXIS));
            pnlPaso.setAlignmentX(Component.LEFT_ALIGNMENT);

            // 2. Detectar si es un paso de "término" para ponerle fondo celeste claro
            boolean esPasoTermino = paso[0].toLowerCase().contains("termino") || paso[0].toLowerCase().contains("término");
            Color colorFondo = esPasoTermino ? new Color(0xF2, 0xFA, 0xFD) : Color.WHITE;
            pnlPaso.setBackground(colorFondo);

            // 3. Bordes y márgenes
            if (!esPasoTermino && i < pasos.size() - 1) {
                pnlPaso.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xEA, 0xEC, 0xF0)), // Línea gris
                        BorderFactory.createEmptyBorder(12, 10, 12, 10)
                ));
            } else {
                pnlPaso.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
            }

            // 4. Etiqueta de Explicación (Gris claro)
            if (paso[0] != null && !paso[0].isEmpty()) {
                JLabel lblExplicacion = new JLabel("<html><body style='width:" + (anchoDisponible - 40) + "px'>" + paso[0] + "</body></html>");
                lblExplicacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblExplicacion.setForeground(new Color(0x8E, 0x98, 0xA8));
                lblExplicacion.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlPaso.add(lblExplicacion);
                pnlPaso.add(Box.createVerticalStrut(8));
            }

            // 5. Etiqueta de Fórmula (Negro, Cambria Math)
            if (paso[1] != null && !paso[1].isEmpty()) {
                JLabel lblFormula = new JLabel("<html><body style='width:" + (anchoDisponible - 40) + "px'>" + paso[1] + "</body></html>");
                lblFormula.setFont(new Font("Cambria Math", Font.BOLD | Font.ITALIC, 17));
                lblFormula.setForeground(new Color(0x11, 0x11, 0x11));
                lblFormula.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlPaso.add(lblFormula);
            }

            pnlPasosContenedor.add(pnlPaso);
        }

        pnlPasosContenedor.revalidate();
        pnlPasosContenedor.repaint();
    }

    // =========================================================
    // MÉTODO PARA CARGAR DATOS DESDE EL HISTORIAL
    // =========================================================
    public void cargarProcedimientoParaEditar(Procedimiento proc) {
        txtFuncion.setText(proc.getFuncion());
        cbxVariable.setSelectedItem(proc.getVariable());

        if (proc.getLimiteInferior() != null && !proc.getLimiteInferior().isEmpty()) {
            txtLimiteInferior.setText(proc.getLimiteInferior());
        } else {
            txtLimiteInferior.setText("");
        }

        if (proc.getLimiteSuperior() != null && !proc.getLimiteSuperior().isEmpty()) {
            txtLimiteSuperior.setText(proc.getLimiteSuperior());
        } else {
            txtLimiteSuperior.setText("");
        }

        lblTipoOperacion.setText("Sin operacion");
        lblFormulaIngresada.setText(" ");
        lblResultadoGrande.setText(" ");
        lblResultadoCaption.setText(" ");
        pnlPasosContenedor.removeAll();
        pnlPasosContenedor.revalidate();
        pnlPasosContenedor.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVerGrafica = new javax.swing.JButton();
        pnlProcedimiento = new javax.swing.JPanel();
        lblProcedimiento = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelPasos = new javax.swing.JPanel();
        pnlPasosContenedor = new javax.swing.JPanel();
        btnHistorial = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblLogoHeader = new javax.swing.JLabel();
        lblIntegraHeader = new javax.swing.JLabel();
        lblLogo2 = new javax.swing.JLabel();
        lblTituloApp = new javax.swing.JLabel();
        pnlSesion = new javax.swing.JPanel();
        lblInicial = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        pnlConfig = new javax.swing.JPanel();
        lblConfigtitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblFuncion = new javax.swing.JLabel();
        txtFuncion = new javax.swing.JTextField();
        lblVariable = new javax.swing.JLabel();
        cbxVariable = new javax.swing.JComboBox<>();
        lblLimInf = new javax.swing.JLabel();
        txtLimiteInferior = new javax.swing.JTextField();
        lblLimiteSuperior = new javax.swing.JLabel();
        txtLimiteSuperior = new javax.swing.JTextField();
        btnDerivar = new javax.swing.JButton();
        btnIntegrar = new javax.swing.JButton();
        pnlResultado = new javax.swing.JPanel();
        lblResultadoTitulo = new javax.swing.JLabel();
        lblTipoOperacion = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(java.awt.Color.decode("#E8F2FF"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblFormulaIngresada = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblResultadoGrande = new javax.swing.JLabel();
        lblResultadoCaption = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 247, 252));
        setMinimumSize(new java.awt.Dimension(765, 445));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVerGrafica.setText("Ver Grafica");
        btnVerGrafica.addActionListener(this::btnVerGraficaActionPerformed);
        getContentPane().add(btnVerGrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 530, -1, -1));

        pnlProcedimiento.setBackground(new java.awt.Color(255, 255, 255));
        pnlProcedimiento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProcedimiento.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblProcedimiento.setText("Procedimiento");
        pnlProcedimiento.add(lblProcedimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Integracion termino a termino y evaluacion en los limites");
        pnlProcedimiento.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        PanelPasos.setBackground(new java.awt.Color(255, 255, 255));
        PanelPasos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelPasos.add(pnlPasosContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        pnlProcedimiento.add(PanelPasos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 480, 200));

        getContentPane().add(pnlProcedimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 225, 520, 290));

        btnHistorial.setText("Ver Historial");
        btnHistorial.addActionListener(this::btnHistorialActionPerformed);
        getContentPane().add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, -1, -1));

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
        lblLogo2.getAccessibleContext().setAccessibleDescription("");

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

        pnlConfig.setBackground(new java.awt.Color(255, 255, 255));
        pnlConfig.setPreferredSize(new java.awt.Dimension(1060, 150));
        pnlConfig.setRequestFocusEnabled(false);
        pnlConfig.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblConfigtitulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblConfigtitulo.setText("Configura tu operacion");
        pnlConfig.add(lblConfigtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Escribe una funcion y selecciona la variable y el intervalo.");
        pnlConfig.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 33, -1, -1));

        lblFuncion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFuncion.setForeground(new java.awt.Color(102, 102, 102));
        lblFuncion.setText("Funcion:");
        pnlConfig.add(lblFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 58, -1, -1));
        pnlConfig.add(txtFuncion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 76, 500, 38));

        lblVariable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVariable.setForeground(new java.awt.Color(102, 102, 102));
        lblVariable.setText("Variable");
        pnlConfig.add(lblVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(545, 58, -1, -1));

        cbxVariable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "x", "y" }));
        cbxVariable.addActionListener(this::cbxVariableActionPerformed);
        pnlConfig.add(cbxVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(545, 76, 100, 38));

        lblLimInf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLimInf.setForeground(new java.awt.Color(102, 102, 102));
        lblLimInf.setText("Limite Inferior");
        pnlConfig.add(lblLimInf, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 58, -1, -1));

        txtLimiteInferior.addActionListener(this::txtLimiteInferiorActionPerformed);
        pnlConfig.add(txtLimiteInferior, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 76, 90, 38));

        lblLimiteSuperior.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLimiteSuperior.setForeground(new java.awt.Color(102, 102, 102));
        lblLimiteSuperior.setText("Limite superior");
        pnlConfig.add(lblLimiteSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 58, -1, -1));
        pnlConfig.add(txtLimiteSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 76, 90, 38));

        btnDerivar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDerivar.setForeground(new java.awt.Color(0, 51, 255));
        btnDerivar.setText("Derivar");
        btnDerivar.addActionListener(this::btnDerivarActionPerformed);
        pnlConfig.add(btnDerivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(875, 76, 80, 38));

        btnIntegrar.setBackground(new java.awt.Color(0, 51, 204));
        btnIntegrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIntegrar.setForeground(new java.awt.Color(255, 255, 255));
        btnIntegrar.setText("Integrar");
        btnIntegrar.addActionListener(this::btnIntegrarActionPerformed);
        pnlConfig.add(btnIntegrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 76, 80, 38));

        getContentPane().add(pnlConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 65, 1060, 150));

        pnlResultado.setBackground(new java.awt.Color(255, 255, 255));
        pnlResultado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblResultadoTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblResultadoTitulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblResultadoTitulo.setForeground(new java.awt.Color(29, 42, 82));
        lblResultadoTitulo.setText("Resultado");
        pnlResultado.add(lblResultadoTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 300, 22));

        lblTipoOperacion.setBackground(new java.awt.Color(232, 242, 255));
        lblTipoOperacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipoOperacion.setForeground(new java.awt.Color(48, 113, 196));
        lblTipoOperacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pnlResultado.add(lblTipoOperacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, 160, 24));

        lblFormulaIngresada.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        lblFormulaIngresada.setForeground(new java.awt.Color(102, 115, 144));
        lblFormulaIngresada.setText(" ");
        lblFormulaIngresada.setToolTipText("f(x) = ");
        pnlResultado.add(lblFormulaIngresada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 420, 26));
        pnlResultado.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 480, 2));

        lblResultadoGrande.setFont(new java.awt.Font("Cambria Math", 1, 32)); // NOI18N
        lblResultadoGrande.setForeground(new java.awt.Color(29, 42, 82));
        lblResultadoGrande.setText(" ");
        pnlResultado.add(lblResultadoGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 480, 80));

        lblResultadoCaption.setForeground(new java.awt.Color(102, 115, 144));
        lblResultadoCaption.setText(" ");
        lblResultadoCaption.setToolTipText("");
        pnlResultado.add(lblResultadoCaption, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 480, 40));

        getContentPane().add(pnlResultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 225, 520, 290));

        btnCerrar.setText("Cerrar Sesion");
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 530, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLimiteInferiorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLimiteInferiorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLimiteInferiorActionPerformed
    private String formatearNumero(double numero) {
        // El formato "#.##" asegura un máximo de 2 decimales. 
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
        if (valor == 0) {
            return "0";
        }
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
            if (error < 1e-9) {
                break;
            }
        }

        long divisor = mcd(mejorNum, mejorDen);
        mejorNum /= divisor;
        mejorDen /= divisor;

        if (mejorDen == 1) {
            return String.valueOf(signo * mejorNum);
        }
        return (signo < 0 ? "-" : "") + mejorNum + "/" + mejorDen;
    }

    private String formatoPolinomio(double coef, double exp, String variable) {
        if (coef == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        // 1. Manejo del coeficiente (Ocultar el 1 o el -1)
        if (coef == -1 && exp != 0) {
            sb.append("-"); // Si es -1x, solo ponemos el "-"
        } else if (coef != 1 || exp == 0) {
            // AQUÍ ESTÁ EL CAMBIO: Usamos doubleToFraccion en lugar de formatearNumero
            sb.append(doubleToFraccion(coef));
        }

        // 2. Manejo de la variable y el exponente con HTML
        if (exp != 0) {
            sb.append(variable); // Ponemos la x o y
            if (exp != 1) {
                // AQUÍ TAMBIÉN: Usamos doubleToFraccion para que exponentes decimales se vean como fracción (ej. 1/2)
                sb.append("<sup>").append(doubleToFraccion(exp)).append("</sup>");
            }
        }

        return sb.toString();
    }

    private void guardarEnHistorial(String tipoCalculo, String limInf, String limSup, String pasos, String resultado) {
        if (this.usuarioActual != null) {
            int idUsuario = this.usuarioActual.getId();
            String funcionStr = txtFuncion.getText();
            String variableStr = cbxVariable.getSelectedItem().toString();

            Procedimiento nuevoProcedimiento = new Procedimiento(
                    idUsuario, funcionStr, variableStr, tipoCalculo, limInf, limSup, resultado, pasos
            );

            ProcedimientoDAO procDAO = new ProcedimientoDAO();
            procDAO.guardarProcedimiento(nuevoProcedimiento);
            System.out.println("Operacion guardada en el historial: " + tipoCalculo);
        }
    }
    private void btnDerivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDerivarActionPerformed
        try {
            String textoFuncion = txtFuncion.getText();
            String variable = cbxVariable.getSelectedItem().toString();

            Parser parser = new Parser();
            List<Polinomio> terminos = parser.parsearFuncion(textoFuncion, variable);

            StringBuilder resultadoFinal = new StringBuilder();
            List<String[]> pasosVisuales = new ArrayList<>();

            pasosVisuales.add(new String[]{"", "d/d" + variable + " (" + textoFuncion + ")"});

            if (terminos.size() > 1) {
                StringBuilder sumaDerivadas = new StringBuilder("= ");
                for (int i = 0; i < terminos.size(); i++) {
                    Polinomio t = terminos.get(i);
                    String termOriginal = formatoPolinomio(t.getCoeficiente(), t.getExponente(), variable);
                    if (i > 0 && t.getCoeficiente() > 0) {
                        sumaDerivadas.append(" + ");
                    }
                    sumaDerivadas.append("d/d").append(variable).append("(").append(termOriginal).append(")");
                }
                pasosVisuales.add(new String[]{"Aplicar la regla de la suma/resta:", sumaDerivadas.toString()});
            }

            for (int i = 0; i < terminos.size(); i++) {
                Polinomio termino = terminos.get(i);
                Polinomio terminoDerivado = termino.derivar();
                double coef = terminoDerivado.getCoeficiente();
                double exp = terminoDerivado.getExponente();

                String terminoOriginalStr = formatoPolinomio(termino.getCoeficiente(), termino.getExponente(), variable);
                String terminoDerivadoStr = formatoPolinomio(coef, exp, variable);
                if (terminoDerivadoStr.isEmpty()) {
                    terminoDerivadoStr = "0";
                }

                pasosVisuales.add(new String[]{"término", "d/d" + variable + "(" + terminoOriginalStr + ") = " + terminoDerivadoStr});

                if (coef != 0) {
                    if (coef > 0 && resultadoFinal.length() > 0) {
                        resultadoFinal.append("+");
                    }
                    resultadoFinal.append(terminoDerivadoStr);
                }
            }

            if (resultadoFinal.length() == 0 || resultadoFinal.toString().equals("+0") || resultadoFinal.toString().equals("-0")) {
                resultadoFinal = new StringBuilder("0");
            }

            String resultadoStr = resultadoFinal.toString();
            pasosVisuales.add(new String[]{"Resultado final", "= " + resultadoStr});

            lblTipoOperacion.setText("Derivada");
            lblFormulaIngresada.setText("<html>f(" + variable + ") = " + textoFuncion + "</html>");
            lblResultadoGrande.setText("<html>f'(" + variable + ") = " + resultadoStr + "</html>");
            lblResultadoCaption.setText("Derivada de la función respecto a " + variable + ".");
            mostrarPasosVisual(pasosVisuales);

            StringBuilder pasosTexto = new StringBuilder();
            for (String[] p : pasosVisuales) {
                pasosTexto.append(p[0]).append("\n").append(p[1]).append("\n");
            }
            guardarEnHistorial("Derivada", "", "", pasosTexto.toString(), resultadoStr);

        } catch (NumberFormatException e) {
            lblResultadoGrande.setText("Error de sintaxis");
            lblResultadoCaption.setText("Revise que la función esté bien escrita.");
        } catch (Exception e) {
            lblResultadoGrande.setText("Error");
            lblResultadoCaption.setText(e.getMessage());
        }
    }//GEN-LAST:event_btnDerivarActionPerformed

    private void btnIntegrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntegrarActionPerformed
        try {
            String textoFuncion = txtFuncion.getText();
            String variable = cbxVariable.getSelectedItem().toString();
            String strLimiteInferior = txtLimiteInferior.getText().trim();
            String strLimiteSuperior = txtLimiteSuperior.getText().trim();

            boolean infVacio = strLimiteInferior.isEmpty();
            boolean supVacio = strLimiteSuperior.isEmpty();

            if (infVacio && supVacio) {
                // ===== CASO 1: INTEGRAL INDEFINIDA =====
                Parser parser = new Parser();
                List<Polinomio> terminos = parser.parsearFuncion(textoFuncion, variable);
                StringBuilder resultadoFinal = new StringBuilder();
                List<String[]> pasosVisuales = new ArrayList<>();

                pasosVisuales.add(new String[]{"", "∫ (" + textoFuncion + ") d" + variable});

                if (terminos.size() > 1) {
                    StringBuilder sumaIntegrales = new StringBuilder("= ");
                    for (int i = 0; i < terminos.size(); i++) {
                        Polinomio t = terminos.get(i);
                        String termOriginal = formatoPolinomio(t.getCoeficiente(), t.getExponente(), variable);
                        if (i > 0 && t.getCoeficiente() > 0) {
                            sumaIntegrales.append(" + ");
                        }
                        sumaIntegrales.append("∫ ").append(termOriginal).append(" d").append(variable);
                    }
                    pasosVisuales.add(new String[]{"Aplicar la regla de la suma: ∫ f(x) ± g(x) dx = ∫ f(x) dx ± ∫ g(x) dx", sumaIntegrales.toString()});
                }

                for (Polinomio termino : terminos) {
                    Polinomio terminoIntegrado = termino.integrar();
                    double coef = terminoIntegrado.getCoeficiente();
                    double exp = terminoIntegrado.getExponente();

                    String terminoOriginalStr = formatoPolinomio(termino.getCoeficiente(), termino.getExponente(), variable);
                    String terminoIntegradoStr = formatoPolinomio(coef, exp, variable);

                    pasosVisuales.add(new String[]{"término", "∫ " + terminoOriginalStr + " d" + variable + " = " + terminoIntegradoStr});

                    if (coef > 0 && resultadoFinal.length() > 0) {
                        resultadoFinal.append("+");
                    }
                    resultadoFinal.append(terminoIntegradoStr);
                }

                if (resultadoFinal.length() == 0) {
                    resultadoFinal.append("0");
                }

                pasosVisuales.add(new String[]{"", "= " + resultadoFinal.toString()});
                pasosVisuales.add(new String[]{"Agregar una constante a la solución", "= " + resultadoFinal.toString() + " + C"});

                String resultadoStr = resultadoFinal.toString() + " + C";

                lblTipoOperacion.setText("Integral indefinida");
                lblFormulaIngresada.setText("<html>∫ (" + textoFuncion + ") d" + variable + "</html>");
                lblResultadoGrande.setText("<html>" + resultadoStr + "</html>");
                lblResultadoCaption.setText("Antiderivada general de la función.");
                mostrarPasosVisual(pasosVisuales);

                StringBuilder pasosTexto = new StringBuilder();
                for (String[] p : pasosVisuales) {
                    pasosTexto.append(p[0]).append("\n").append(p[1]).append("\n");
                }
                guardarEnHistorial("Integral indefinida", "", "", pasosTexto.toString(), resultadoStr);

            } else if (!infVacio && !supVacio) {
                // ===== CASO 2: INTEGRAL DEFINIDA =====
                try {
                    double limiteInferior = Double.parseDouble(strLimiteInferior);
                    double limiteSuperior = Double.parseDouble(strLimiteSuperior);

                    Parser parser = new Parser();
                    List<Polinomio> terminos = parser.parsearFuncion(textoFuncion, variable);

                    double resultadoAcumulado = 0;
                    List<String[]> pasosVisuales = new ArrayList<>();

                    pasosVisuales.add(new String[]{"", "∫ [" + formatearNumero(limiteInferior) + ", " + formatearNumero(limiteSuperior) + "] (" + textoFuncion + ") d" + variable});

                    StringBuilder antiderivadaStr = new StringBuilder();
                    double evalSupTotal = 0;
                    double evalInfTotal = 0;

                    for (Polinomio termino : terminos) {
                        Polinomio terminoIntegrado = termino.integrar();
                        double evalSup = terminoIntegrado.evaluar(limiteSuperior);
                        double evalInf = terminoIntegrado.evaluar(limiteInferior);
                        evalSupTotal += evalSup;
                        evalInfTotal += evalInf;
                        resultadoAcumulado += (evalSup - evalInf);

                        double coef = terminoIntegrado.getCoeficiente();
                        double exp = terminoIntegrado.getExponente();

                        String terminoIntegradoStr = formatoPolinomio(coef, exp, variable);
                        if (coef > 0 && antiderivadaStr.length() > 0) {
                            antiderivadaStr.append("+");
                        }
                        antiderivadaStr.append(terminoIntegradoStr);
                    }

                    pasosVisuales.add(new String[]{"Calcular la antiderivada (Integral indefinida):", "= " + antiderivadaStr.toString()});
                    pasosVisuales.add(new String[]{"Evaluar los límites usando la Regla de Barrow: F(b) - F(a)", "= [" + antiderivadaStr.toString() + "] desde " + formatearNumero(limiteInferior) + " hasta " + formatearNumero(limiteSuperior)});
                    pasosVisuales.add(new String[]{"Sustituir el límite superior (" + formatearNumero(limiteSuperior) + ") y restar el límite inferior (" + formatearNumero(limiteInferior) + ")", "= (" + formatearNumero(evalSupTotal) + ") - (" + formatearNumero(evalInfTotal) + ")"});

                    String fraccionStr = doubleToFraccion(resultadoAcumulado);
                    String decimalStr = formatearNumero(resultadoAcumulado);
                    String resultadoStr = fraccionStr;
                    pasosVisuales.add(new String[]{"Resultado final", "= " + fraccionStr + " ≈ " + decimalStr});

                    lblTipoOperacion.setText("Integral definida");
                    lblFormulaIngresada.setText("<html>∫[" + formatearNumero(limiteInferior) + ", " + formatearNumero(limiteSuperior) + "] (" + textoFuncion + ") d" + variable + "</html>");
                    lblResultadoGrande.setText("<html><div style='font-size:32px;'>" + fraccionStr
                            + "</div><div style='font-size:14px; color:#3071C4; font-weight:bold; margin-top:4px;'>&#8776; "
                            + decimalStr + "</div></html>");
                    lblResultadoCaption.setText("Valor de la integral en el intervalo dado.");
                    lblResultadoGrande.setBounds(20, 120, 480, 70);
                    lblResultadoCaption.setBounds(20, 200, 480, 40);
                    mostrarPasosVisual(pasosVisuales);

                    StringBuilder pasosTexto = new StringBuilder();
                    for (String[] p : pasosVisuales) {
                        pasosTexto.append(p[0]).append("\n").append(p[1]).append("\n");
                    }
                    guardarEnHistorial("Integral definida", strLimiteInferior, strLimiteSuperior, pasosTexto.toString(), resultadoStr);

                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Los límites deben ser números válidos.", "Error de formato", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe ingresar ambos límites o dejarlos vacíos.", "Límites incompletos", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            lblResultadoGrande.setText("Error");
            lblResultadoCaption.setText("Revise que la función esté bien escrita.");
        }
    }//GEN-LAST:event_btnIntegrarActionPerformed

    private void cbxVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxVariableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxVariableActionPerformed

    private void btnVerGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerGraficaActionPerformed
        String funcion = txtFuncion.getText().trim();
        String variable = cbxVariable.getSelectedItem().toString();
        String limInf = txtLimiteInferior.getText().trim();
        String limSup = txtLimiteSuperior.getText().trim();
        
        // CORRECCIÓN: El resultado se obtiene de lblResultadoGrande
        // Usamos replaceAll para quitar las etiquetas HTML que le pones al resultado
        String resultado = lblResultadoGrande.getText().replaceAll("<[^>]*>", "").trim(); 

        // Validamos que haya una función escrita
        if (funcion.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese una función y presione 'Integrar' antes de ver la gráfica.", 
                "Datos incompletos", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Abrimos la ventana gráfica y le pasamos todos los datos
        new VentanaGrafica(this.usuarioActual, funcion, variable, limInf, limSup, resultado).setVisible(true);
        
        // Cerramos la calculadora actual
        this.dispose();
    }//GEN-LAST:event_btnVerGraficaActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed

        if (this.usuarioActual != null) {
            // Abre la ventana de historial y le pasa el usuario actual
            new VentanaHistorial(this.usuarioActual).setVisible(true);
            this.dispose(); // Cierra la calculadora para no tener ventanas amontonadas
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: No hay sesión activa.");
        }
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // 1. Mostramos una ventana de confirmación
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Cerrar Sesión",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        // 2. Verificamos si el usuario hizo clic en "Sí"
        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            
            // Opcional: Si tienes alguna variable global estática del usuario, la limpias aquí
            // this.usuarioActual = null; 
            
            // 3. Abrimos la ventana de Login nuevamente
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
            
            // 4. Cerramos y destruimos la ventana actual de la calculadora
            this.dispose();
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPasos;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnDerivar;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnIntegrar;
    private javax.swing.JButton btnVerGrafica;
    private javax.swing.JComboBox<String> cbxVariable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblConfigtitulo;
    private javax.swing.JLabel lblFormulaIngresada;
    private javax.swing.JLabel lblFuncion;
    private javax.swing.JLabel lblInicial;
    private javax.swing.JLabel lblIntegraHeader;
    private javax.swing.JLabel lblLimInf;
    private javax.swing.JLabel lblLimiteSuperior;
    private javax.swing.JLabel lblLogo2;
    private javax.swing.JLabel lblLogoHeader;
    private javax.swing.JLabel lblProcedimiento;
    private javax.swing.JLabel lblResultadoCaption;
    private javax.swing.JLabel lblResultadoGrande;
    private javax.swing.JLabel lblResultadoTitulo;
    private javax.swing.JLabel lblTipoOperacion;
    private javax.swing.JLabel lblTituloApp;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVariable;
    private javax.swing.JPanel pnlConfig;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlPasosContenedor;
    private javax.swing.JPanel pnlProcedimiento;
    private javax.swing.JPanel pnlResultado;
    private javax.swing.JPanel pnlSesion;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextField txtLimiteInferior;
    private javax.swing.JTextField txtLimiteSuperior;
    // End of variables declaration//GEN-END:variables
}
