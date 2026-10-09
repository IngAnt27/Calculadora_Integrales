/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Usuario
 */
package calculadoraintegrales.frontend.vistas;

import calculadoraintegrales.frontend.componentes.PanelDegradado;
import calculadoraintegrales.backend.dao.UsuarioDAO;
import calculadoraintegrales.backend.util.Seguridad;
import calculadoraintegrales.backend.model.Usuario;

  

    public class VentanaLogin extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaLogin.class.getName());

        public VentanaLogin() {
            initComponents();
            this.setLocationRelativeTo(null); // Centra la ventana al abrirse
            aplicarEstilosPersonalizados();

            // ===== 1. CARGAR USUARIO GUARDADO (RECORDARME) =====
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(VentanaLogin.class);
            String usuarioGuardado = prefs.get("usuario_guardado", "");
            if (!usuarioGuardado.isEmpty()) {
                txtUsuario.setText(usuarioGuardado);
                chkRecordarme.setSelected(true);
                // Si el usuario ya está lleno, el cursor debe ir directo a la contraseña
                javax.swing.SwingUtilities.invokeLater(() -> txtPassword.requestFocusInWindow());
            } else {
                // Si no hay nada guardado, el cursor va al campo de usuario
                javax.swing.SwingUtilities.invokeLater(() -> txtUsuario.requestFocusInWindow());
            }

            // ===== 2. NAVEGACIÓN CON TECLADO (Enter y Flechas) =====
            // De Usuario a Contraseña al presionar ENTER o Flecha Abajo
            txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
                        txtPassword.requestFocusInWindow();
                    }
                }
            });

            // De Contraseña a Usuario (Flecha Arriba) o a Iniciar Sesión (ENTER)
            txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                        btnLogin.doClick(); // Simula un clic en el botón de login
                    } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                        txtUsuario.requestFocusInWindow();
                    }
                }
            });

            // ===== 3. ACCIÓN PARA "OLVIDASTE TU CONTRASEÑA" =====
            lblOlvidaste.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Como aún no hay ventana de recuperación, mostramos un mensaje informativo
                    javax.swing.JOptionPane.showMessageDialog(VentanaLogin.this,
                            "Para recuperar el acceso a su cuenta, por favor comuníquese con el administrador del sistema.",
                            "Recuperación de contraseña",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    lblOlvidaste.setForeground(new java.awt.Color(0, 153, 255)); // Se ilumina al pasar el ratón
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    lblOlvidaste.setForeground(new java.awt.Color(0, 51, 153)); // Vuelve a su color original
                }
            });
    }

    private void aplicarEstilosPersonalizados() {

        // ===== FONDO DEL PANEL DERECHO =====
        pnlDerecho.setBackground(new java.awt.Color(255, 255, 255)); // Blanco puro como la referencia
        pnlDerecho.setOpaque(true);

        // ===== CORRECCIÓN DE TEXTOS INVISIBLES (Sobreponiéndose a FlatDarkLaf) =====
        lblAccede.setForeground(new java.awt.Color(48, 113, 196));
        lblBienvenido.setForeground(new java.awt.Color(29, 42, 82));
        lblSubtitulo.setForeground(new java.awt.Color(102, 115, 144));
        lblUsuario.setText("Usuario"); 
        lblUsuario.setForeground(new java.awt.Color(29, 42, 82));
        jLabel2.setForeground(new java.awt.Color(29, 42, 82));
        chkRecordarme.setForeground(new java.awt.Color(102, 115, 144));
        lblOlvidaste.setForeground(new java.awt.Color(48, 113, 196));

        // ===== CAMPO USUARIO =====
        txtUsuario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtUsuario.setBackground(java.awt.Color.WHITE);
        txtUsuario.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        txtUsuario.putClientProperty("FlatLaf.style",
                "background:#FFFFFF; arc:14; borderColor:#D7DCEA; focusedBorderColor:#3071C4; focusWidth:1");
        txtUsuario.putClientProperty("JTextField.placeholderText", "Usuario");
        txtUsuario.setMargin(new java.awt.Insets(8, 14, 8, 14));

        // ===== CAMPO CONTRASEÑA =====
        txtPassword.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtPassword.setBackground(java.awt.Color.WHITE);
        txtPassword.setForeground(new java.awt.Color(0x1D, 0x2A, 0x52));
        txtPassword.putClientProperty("FlatLaf.style",
                "background:#FFFFFF; arc:14; borderColor:#D7DCEA; focusedBorderColor:#3071C4; focusWidth:1");
        txtPassword.putClientProperty("JTextField.placeholderText", "••••••••");
        txtPassword.putClientProperty("JPasswordField.showRevealButton", true);
        txtPassword.setMargin(new java.awt.Insets(8, 14, 8, 14));

        // ===== BOTÓN LOGIN =====
        btnLogin.setText("Iniciar sesión");
        btnLogin.setBackground(new java.awt.Color(55, 137, 198)); // Azul elegante de la referencia
        btnLogin.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        btnLogin.setForeground(java.awt.Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.putClientProperty("JComponent.arc", 14);

        // ===== ENLACE "CREAR CUENTA" =====
        btnRegistro.setText("<html>¿Aún no tienes cuenta? "
                + "<span style='color:#3071C4; font-weight:bold;'>Crear cuenta</span></html>");
        btnRegistro.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        btnRegistro.setForeground(new java.awt.Color(102, 115, 144)); // Gris para la primera parte
        btnRegistro.setContentAreaFilled(false);
        btnRegistro.setBorderPainted(false);
        btnRegistro.setFocusPainted(false);
        btnRegistro.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.UIManager.put("Component.focusColor", new java.awt.Color(0x30, 0x71, 0xC4));
        javax.swing.UIManager.put("Component.focusWidth", 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlIzquierdo = new PanelDegradado();
        lblSimbolo = new javax.swing.JLabel();
        lblTitulo1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlDerecho = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnRegistro = new javax.swing.JButton();
        lblAccede = new javax.swing.JLabel();
        lblBienvenido = new javax.swing.JLabel();
        chkRecordarme = new javax.swing.JCheckBox();
        lblSubtitulo = new javax.swing.JLabel();
        lblOlvidaste = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 500));
        setSize(new java.awt.Dimension(950, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlIzquierdo.setPreferredSize(new java.awt.Dimension(650, 460));

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(193, 199, 218));
        jLabel3.setText("Resuelve explora y aprende calculo");
        jLabel3.setPreferredSize(new java.awt.Dimension(345, 54));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(193, 199, 218));
        jLabel4.setText("con una herramienta echa para ti.");
        jLabel4.setPreferredSize(new java.awt.Dimension(345, 54));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoraintegrales/encabezado.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(30, 54));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoraintegrales/Screenshot 2026-10-02 053249.png"))); // NOI18N
        jLabel5.setText("jLabel5");
        jLabel5.setPreferredSize(new java.awt.Dimension(430, 54));

        javax.swing.GroupLayout pnlIzquierdoLayout = new javax.swing.GroupLayout(pnlIzquierdo);
        pnlIzquierdo.setLayout(pnlIzquierdoLayout);
        pnlIzquierdoLayout.setHorizontalGroup(
            pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIzquierdoLayout.createSequentialGroup()
                .addGroup(pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIzquierdoLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlIzquierdoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblSimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlIzquierdoLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        pnlIzquierdoLayout.setVerticalGroup(
            pnlIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIzquierdoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSimbolo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlIzquierdo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 500));

        pnlDerecho.setPreferredSize(new java.awt.Dimension(650, 440));
        pnlDerecho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(102, 102, 102));
        lblUsuario.setText("Usuario");
        pnlDerecho.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtUsuario.addActionListener(this::txtUsuarioActionPerformed);
        pnlDerecho.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 240, 33));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Contraseña");
        pnlDerecho.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 110, 30));

        txtPassword.addActionListener(this::txtPasswordActionPerformed);
        pnlDerecho.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 240, 30));

        btnLogin.setBackground(new java.awt.Color(0, 153, 204));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("INICIAR SESION");
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        pnlDerecho.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 250, 54));

        btnRegistro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(0, 51, 153));
        btnRegistro.setText("Crear Cuenta");
        btnRegistro.addActionListener(this::btnRegistroActionPerformed);
        pnlDerecho.add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, -1, -1));

        lblAccede.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAccede.setForeground(new java.awt.Color(0, 102, 204));
        lblAccede.setText("ACCEDE A TU ESPACIO");
        lblAccede.setPreferredSize(new java.awt.Dimension(30, 60));
        pnlDerecho.add(lblAccede, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 180, 30));

        lblBienvenido.setFont(new java.awt.Font("Segoe UI", 1, 31)); // NOI18N
        lblBienvenido.setText("Bienvenido de nuevo");
        pnlDerecho.add(lblBienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        chkRecordarme.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkRecordarme.setForeground(new java.awt.Color(51, 51, 51));
        chkRecordarme.setText("Recordarme");
        chkRecordarme.addActionListener(this::chkRecordarmeActionPerformed);
        pnlDerecho.add(chkRecordarme, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, -1, -1));

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(153, 153, 153));
        lblSubtitulo.setText("Inicia sesion para continuar con tus calculos");
        pnlDerecho.add(lblSubtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        lblOlvidaste.setForeground(new java.awt.Color(0, 51, 153));
        lblOlvidaste.setText("Olvidaste tu contraseña?");
        lblOlvidaste.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDerecho.add(lblOlvidaste, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, -1, -1));
        pnlDerecho.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 170, 40));
        pnlDerecho.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 170, 20));

        getContentPane().add(pnlDerecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 490, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        this.dispose(); // Cierra la ventana de login
        new VentanaRegistro().setVisible(true); // Abre la ventana de registro
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
       String nombreUsuario = txtUsuario.getText().trim();
        String passwordStr = new String(txtPassword.getPassword()).trim();

        if (nombreUsuario.isEmpty() || passwordStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese usuario y contraseña.", "Campos vacíos", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String hash = Seguridad.hashearContrasena(passwordStr);
        UsuarioDAO dao = new UsuarioDAO();

        // Obtenemos el usuario de la base de datos
        Usuario usuarioValidado = dao.validarLogin(nombreUsuario, hash);

        if (usuarioValidado != null) {
            
            // ===== LÓGICA DE RECORDAR USUARIO =====
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(VentanaLogin.class);
            if (chkRecordarme.isSelected()) {
                // Guarda el usuario de forma persistente y segura en Windows
                prefs.put("usuario_guardado", nombreUsuario);
            } else {
                // Si la casilla no está marcada, borra el usuario guardado
                prefs.remove("usuario_guardado");
            }
            
            // Pasamos el usuario validado directamente al constructor de la calculadora
            new VentanaCalculadora(usuarioValidado).setVisible(true);
            this.dispose(); // Cierra el login
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            txtPassword.setText(""); // Limpia la contraseña para que vuelva a intentar
            txtPassword.requestFocusInWindow(); // Devuelve el cursor a la contraseña
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        txtUsuario.putClientProperty("JComponent.arc", 12);
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void chkRecordarmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRecordarmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkRecordarmeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JCheckBox chkRecordarme;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAccede;
    private javax.swing.JLabel lblBienvenido;
    private javax.swing.JLabel lblOlvidaste;
    private javax.swing.JLabel lblSimbolo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlDerecho;
    private javax.swing.JPanel pnlIzquierdo;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
