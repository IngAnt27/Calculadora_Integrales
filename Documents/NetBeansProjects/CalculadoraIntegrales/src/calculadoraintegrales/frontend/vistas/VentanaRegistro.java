package calculadoraintegrales.frontend.vistas;


import calculadoraintegrales.frontend.componentes.PanelDegradado;
import calculadoraintegrales.backend.dao.UsuarioDAO;
import calculadoraintegrales.backend.dao.UsuarioDAO;
import calculadoraintegrales.backend.model.Usuario;
import calculadoraintegrales.backend.util.Seguridad;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
public class VentanaRegistro extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaRegistro.class.getName());

    /**
     * Creates new form VentanaRegistro
     */
    public VentanaRegistro() {
        
       initComponents();
        this.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        aplicarEstilosPersonalizados();
        configurarNavegacionTeclado();
        
        // Colocamos el cursor parpadeando en el primer campo al abrir
        javax.swing.SwingUtilities.invokeLater(() -> txtNombre.requestFocusInWindow());
    
        
    }
    private void aplicarEstilosPersonalizados() {
        // ===== FONDO BLANCO =====
        pnlDerecho.setBackground(java.awt.Color.WHITE);
        pnlDerecho.setOpaque(true);

        // ===== COLORES DE TEXTOS =====
        lblUnete.setForeground(new java.awt.Color(48, 113, 196)); 
        lblCreaTuCuenta.setForeground(new java.awt.Color(29, 42, 82)); 
        lblSubtitulo.setForeground(new java.awt.Color(102, 115, 144)); 
        
        lblNombre.setForeground(new java.awt.Color(29, 42, 82));
        lblCorreo.setForeground(new java.awt.Color(29, 42, 82));
        lblPass.setForeground(new java.awt.Color(29, 42, 82));
        lblConfirmar.setForeground(new java.awt.Color(29, 42, 82));
        lblbAyudaPass.setForeground(new java.awt.Color(153, 153, 153));
        chkTerminos.setForeground(new java.awt.Color(102, 115, 144));
        lblTienesCuenta.setForeground(new java.awt.Color(102, 115, 144));
        lblPie.setForeground(new java.awt.Color(153, 153, 153));

        // ===== BORDES CURVOS Y ESTILO PARA TODOS LOS CAMPOS =====
        javax.swing.JTextField[] campos = {txtNombre, txtCorreo, txtPassword, txtConfirmPassword};
        for (javax.swing.JTextField campo : campos) {
            campo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
            campo.setBackground(java.awt.Color.WHITE);
            campo.setForeground(new java.awt.Color(29, 42, 82));
            campo.putClientProperty("FlatLaf.style",
                "background:#FFFFFF; arc:14; borderColor:#D7DCEA; focusedBorderColor:#3071C4; focusWidth:1");
            campo.setMargin(new java.awt.Insets(6, 12, 6, 12));
        }

        // ===== TEXTOS DE FONDO (PLACEHOLDERS) =====
        txtNombre.putClientProperty("JTextField.placeholderText", "Tu nombre");
        txtCorreo.putClientProperty("JTextField.placeholderText", "nombre@correo.com");
        txtPassword.putClientProperty("JTextField.placeholderText", "Crea una contraseña");
        txtConfirmPassword.putClientProperty("JTextField.placeholderText", "Repite tu contraseña");
        
        // Mostrar el "ojito" para revelar contraseña 
        txtPassword.putClientProperty("JPasswordField.showRevealButton", true);
        txtConfirmPassword.putClientProperty("JPasswordField.showRevealButton", true);

        // ===== BOTÓN AZUL PRINCIPAL =====
        btnRegistrar.setBackground(new java.awt.Color(48, 113, 196)); 
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        btnRegistrar.setForeground(java.awt.Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrar.putClientProperty("JComponent.arc", 14);

        // ===== BOTÓN INFERIOR DE INICIAR SESIÓN =====
        btnVolver.setText("<html><span style='color:#3071C4; font-weight:bold;'>Inicia sesión</span></html>");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void configurarNavegacionTeclado() {
        // De Nombre a Correo
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    txtCorreo.requestFocusInWindow();
                }
            }
        });

        // De Correo a Contraseña
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    txtPassword.requestFocusInWindow();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    txtNombre.requestFocusInWindow();
                }
            }
        });

        // De Contraseña a Confirmar Contraseña
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                    txtConfirmPassword.requestFocusInWindow();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    txtCorreo.requestFocusInWindow();
                }
            }
        });

        // De Confirmar Contraseña a Registrar (ENTER dispara el botón)
        txtConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnRegistrar.doClick();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    txtPassword.requestFocusInWindow();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        lblPieD = new PanelDegradado();
        lblSimbolo = new javax.swing.JLabel();
        lblTitulo1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlDerecho = new javax.swing.JPanel();
        lblUnete = new javax.swing.JLabel();
        lblCreaTuCuenta = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnRegistrar = new javax.swing.JButton();
        lblPass = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblConfirmar = new javax.swing.JLabel();
        lblbAyudaPass = new javax.swing.JLabel();
        chkTerminos = new javax.swing.JCheckBox();
        btnVolver = new javax.swing.JButton();
        lblTienesCuenta = new javax.swing.JLabel();
        lblPie = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 63, -1, -1));

        lblPieD.setPreferredSize(new java.awt.Dimension(650, 460));

        lblSimbolo.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblSimbolo.setForeground(new java.awt.Color(0, 255, 255));
        lblSimbolo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSimbolo.setText("mas claras.");
        lblSimbolo.setPreferredSize(new java.awt.Dimension(280, 54));
        lblSimbolo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lblTitulo1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo1.setText("Tus integrales,");
        lblTitulo1.setPreferredSize(new java.awt.Dimension(230, 54));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(193, 199, 218));
        jLabel4.setText("Resuelve explora y aprende calculo");
        jLabel4.setPreferredSize(new java.awt.Dimension(345, 54));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(193, 199, 218));
        jLabel5.setText("con una herramienta echa para ti.");
        jLabel5.setPreferredSize(new java.awt.Dimension(345, 54));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoraintegrales/encabezado.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(30, 54));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoraintegrales/Screenshot 2026-10-02 053249.png"))); // NOI18N
        jLabel7.setText("jLabel5");
        jLabel7.setPreferredSize(new java.awt.Dimension(430, 54));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setText("MATEMATICAS -  VISUAL - INTUITIVA");

        javax.swing.GroupLayout lblPieDLayout = new javax.swing.GroupLayout(lblPieD);
        lblPieD.setLayout(lblPieDLayout);
        lblPieDLayout.setHorizontalGroup(
            lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblPieDLayout.createSequentialGroup()
                .addGroup(lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblPieDLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblSimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(lblPieDLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(lblPieDLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        lblPieDLayout.setVerticalGroup(
            lblPieDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblPieDLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(48, 48, 48))
        );

        getContentPane().add(lblPieD, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 550));

        pnlDerecho.setPreferredSize(new java.awt.Dimension(650, 440));
        pnlDerecho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUnete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUnete.setForeground(new java.awt.Color(0, 102, 204));
        lblUnete.setText("UNETE A UNIVERSO 7");
        lblUnete.setPreferredSize(new java.awt.Dimension(30, 60));
        pnlDerecho.add(lblUnete, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 20, 180, 30));

        lblCreaTuCuenta.setFont(new java.awt.Font("Segoe UI", 1, 31)); // NOI18N
        lblCreaTuCuenta.setText("Crea tu cuenta");
        pnlDerecho.add(lblCreaTuCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 40, -1, -1));

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(153, 153, 153));
        lblSubtitulo.setText("Guarda tus ejercicios y continua aprendiendo");
        pnlDerecho.add(lblSubtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 75, -1, -1));

        txtNombre.addActionListener(this::txtNombreActionPerformed);
        pnlDerecho.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 130, 370, 32));

        txtPassword.addActionListener(this::txtPasswordActionPerformed);
        pnlDerecho.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 250, 370, 32));

        btnRegistrar.setText("Crear Cuenta");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);
        pnlDerecho.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 405, 370, 42));

        lblPass.setText("Contraseña:");
        pnlDerecho.add(lblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 230, -1, -1));

        lblNombre.setText("Nombre Completo");
        pnlDerecho.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 110, -1, -1));

        lblCorreo.setText("Correo electronico");
        pnlDerecho.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 170, -1, -1));

        txtCorreo.addActionListener(this::txtCorreoActionPerformed);
        pnlDerecho.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 190, 370, 32));

        lblConfirmar.setText("Confirmar Contraseña");
        pnlDerecho.add(lblConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 290, -1, -1));

        lblbAyudaPass.setText("Usa al menos 8 caracteres");
        pnlDerecho.add(lblbAyudaPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 345, -1, -1));

        chkTerminos.setText("Acepto los terminos y la politica de privacidad");
        pnlDerecho.add(chkTerminos, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 370, -1, -1));

        btnVolver.setText("Iniciar sesion");
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        pnlDerecho.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 465, -1, -1));

        lblTienesCuenta.setText("Ya tienes una cuenta?");
        pnlDerecho.add(lblTienesCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 470, -1, -1));

        lblPie.setText("Universo 7 .  Calculadora de integrales.");
        pnlDerecho.add(lblPie, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, -1, -1));

        txtConfirmPassword.addActionListener(this::txtConfirmPasswordActionPerformed);
        pnlDerecho.add(txtConfirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 310, 370, 32));

        getContentPane().add(pnlDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 460, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
    
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
  String nombreUsuario = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String passwordStr = new String(txtPassword.getPassword()).trim();
        String confirmarStr = new String(txtConfirmPassword.getPassword()).trim(); 
        
        // 1. Validar que nada esté vacío
        if (nombreUsuario.isEmpty() || correo.isEmpty() || passwordStr.isEmpty() || confirmarStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos incompletos", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validar que las contraseñas sean idénticas
        if (!passwordStr.equals(confirmarStr)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden. Por favor, verifíquelas.", "Error de contraseña", javax.swing.JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocusInWindow();
            return;
        }

        // 3. Validar los 8 caracteres mínimos
        if (passwordStr.length() < 8) {
            javax.swing.JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.", "Contraseña muy corta", javax.swing.JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocusInWindow();
            return;
        }

        // 4. Validar el Checkbox de Términos
        if (!chkTerminos.isSelected()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe aceptar los términos y la política de privacidad para continuar.", "Términos requeridos", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 5. Hash y base de datos
        String hash = Seguridad.hashearContrasena(passwordStr);
        Usuario nuevoUsuario = new Usuario(nombreUsuario, hash);
        UsuarioDAO dao = new UsuarioDAO();
        
        if (dao.registrarUsuario(nuevoUsuario)) {
            javax.swing.JOptionPane.showMessageDialog(this, "¡Cuenta creada con éxito! Ahora puede iniciar sesión.", "Registro exitoso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); 
            new VentanaLogin().setVisible(true); 
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya existe en el sistema. Elija otro.", "Error de registro", javax.swing.JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocusInWindow();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose(); // Cierra registro
        new VentanaLogin().setVisible(true); 
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConfirmPasswordActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new VentanaRegistro().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkTerminos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblConfirmar;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblCreaTuCuenta;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPie;
    private javax.swing.JPanel lblPieD;
    private javax.swing.JLabel lblSimbolo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTienesCuenta;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblUnete;
    private javax.swing.JLabel lblbAyudaPass;
    private javax.swing.JPanel pnlDerecho;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}

