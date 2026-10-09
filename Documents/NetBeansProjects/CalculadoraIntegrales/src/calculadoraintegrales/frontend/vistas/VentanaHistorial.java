package calculadoraintegrales.frontend.vistas;

import calculadoraintegrales.backend.dao.ProcedimientoDAO;
import calculadoraintegrales.backend.model.Procedimiento;
import calculadoraintegrales.backend.model.Usuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class VentanaHistorial extends javax.swing.JFrame {
    private Usuario usuarioActual;
    private java.util.List<Procedimiento> listaActual;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaHistorial.class.getName());
    private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

    /**
     * Creates new form VentanaHistorial
     */
    public VentanaHistorial(Usuario usuario) {
    initComponents();
    
        tblResultados.setSelectionBackground(new java.awt.Color(232, 242, 255)); // Fondo celeste claro al seleccionar
        tblResultados.setSelectionForeground(new java.awt.Color(29, 42, 82));    // Texto azul oscuro al seleccionar
        this.usuarioActual = usuario;
        
        // Forzamos el tamaño y centrado
        this.setSize(1100, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        // ===== AVATAR Y SESIÓN DEL USUARIO =====
        if (this.usuarioActual != null) {
            // Nombre del usuario
            lblUsuario.setText(this.usuarioActual.getNombreUsuario());
            lblUsuario.setForeground(new java.awt.Color(29, 42, 82)); // Azul oscuro
            
            // Lógica para la inicial
            String inicial = (this.usuarioActual.getNombreUsuario() != null && !this.usuarioActual.getNombreUsuario().isEmpty())
                     ? this.usuarioActual.getNombreUsuario().substring(0, 1).toUpperCase() : "?";
                     
            // Círculo de la inicial
            lblInicial.setOpaque(true);
            lblInicial.setBackground(new java.awt.Color(48, 113, 196)); // Azul del tema
            lblInicial.setForeground(java.awt.Color.WHITE);
            lblInicial.setText(inicial);
            lblInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lblInicial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            lblInicial.putClientProperty("JComponent.arc", 999); // Círculo perfecto en FlatLaf
            
            // Fondo redondeado del panel de sesión
            pnlSesion.setBackground(new java.awt.Color(245, 247, 252));
            pnlSesion.putClientProperty("JComponent.arc", 20);
        }
        
        aplicarEstilosPersonalizados();
        cargarHistorial();
    }
    
    
    private void aplicarEstilosPersonalizados() {
        lblIntegraHeader.setForeground(new java.awt.Color(29, 42, 82)); // Azul oscuro
        lblLogo2.setForeground(new java.awt.Color(102, 115, 144));      // Gris para el subtítulo UNIVERSO7
        lblTituloApp.setForeground(new java.awt.Color(29, 42, 82)); // Azul oscuro
        lblTituloApp.setOpaque(false);
        // ===== MAQUILLAJE DE LA TABLA =====
        tblResultados.setBackground(java.awt.Color.WHITE);
        tblResultados.setRowHeight(55); 
        tblResultados.setShowGrid(false); 
        tblResultados.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblResultados.setForeground(new java.awt.Color(29, 42, 82));
        tblResultados.setSelectionBackground(new java.awt.Color(232, 242, 255));
        
        // ===== ENCABEZADO DE LA TABLA =====
        javax.swing.table.JTableHeader header = tblResultados.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(100, 45));
        header.setBackground(new java.awt.Color(240, 244, 248));
        header.setForeground(java.awt.Color.BLACK); // Texto del header en negro
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

        // ===== REDONDEO DEL CONTENEDOR DE LA TABLA =====
        // Le damos un borde gris muy sutil y usamos la propiedad 'arc' de FlatLaf para redondearlo
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 232, 240), 1, true));
        jScrollPane1.putClientProperty("JComponent.arc", 16); 
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);

       // ===== MAQUILLAJE DE BOTONES =====
        // Botón Regresar
        btnRegresar.putClientProperty("JComponent.arc", 15);
        btnRegresar.setBackground(java.awt.Color.WHITE);
        btnRegresar.setForeground(new java.awt.Color(48, 113, 196));
        btnRegresar.setFocusPainted(false);

        // Botón Editar 
        btnEditar.putClientProperty("JComponent.arc", 15);
        btnEditar.setBackground(java.awt.Color.WHITE);
        btnEditar.setForeground(new java.awt.Color(48, 113, 196));
        btnEditar.setFocusPainted(false);

        // Botón Eliminar 
        btnEliminar.putClientProperty("JComponent.arc", 15);
        btnEliminar.setBackground(new java.awt.Color(255, 235, 238)); 
        btnEliminar.setForeground(new java.awt.Color(211, 47, 47));  
        btnEliminar.setFocusPainted(false);
        
        // ===== NUEVOS COMPONENTES SUPERIORES =====
        // Textos principales
        lblTituloCalculos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        lblTituloCalculos.setForeground(new java.awt.Color(29, 42, 82)); // Azul oscuro

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblSubtitulo.setForeground(new java.awt.Color(102, 115, 144)); // Gris

        // Píldora dinámica de conteo de registros
        lblConteoRegistros.setOpaque(true);
        lblConteoRegistros.setBackground(new java.awt.Color(232, 242, 255)); 
        lblConteoRegistros.setForeground(new java.awt.Color(48, 113, 196)); 
        lblConteoRegistros.putClientProperty("JComponent.arc", 15);
        lblConteoRegistros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConteoRegistros.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

       // ===== BUSCADOR Y FILTRO =====
        // Buscador
        txtBuscar.setBackground(java.awt.Color.WHITE); 
        txtBuscar.setForeground(new java.awt.Color(29, 42, 82));
        txtBuscar.putClientProperty("JComponent.arc", 15);
        txtBuscar.putClientProperty("JTextField.placeholderText", "🔍 Buscar función o resultado"); 
        
        // ComboBox de filtro
        cbxFiltro.putClientProperty("JComponent.arc", 15);
        cbxFiltro.setBackground(java.awt.Color.WHITE);
        cbxFiltro.setForeground(new java.awt.Color(29, 42, 82));
        
        
    }
    
    private String formatearMatematicasHTML(String texto) {
       if (texto == null) return "";
        
        // Expresión regular: Busca el símbolo ^ seguido de números/decimales/signos 
        // y los envuelve correctamente en <sup>...</sup>
        String textoFormateado = texto.replaceAll("\\^([\\-\\d\\.]+)", "<sup>$1</sup>");
        
        return "<html><span style='font-family: Cambria Math; font-size: 15px;'>" 
                + textoFormateado 
                + "</span></html>";
    }

    private void cargarHistorial() {
        // Hacemos que las celdas no sean editables al hacer doble clic
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        modelo.addColumn("Tipo");
        modelo.addColumn("Función");
        modelo.addColumn("Variable");
        modelo.addColumn("Lím. Inf.");
        modelo.addColumn("Lím. Sup.");
        modelo.addColumn("Resultado");

        if (this.usuarioActual != null) {
            ProcedimientoDAO dao = new ProcedimientoDAO();
            this.listaActual = dao.obtenerHistorial(this.usuarioActual.getId());

            for (Procedimiento proc : this.listaActual) {
                Object[] fila = new Object[6];
                
                fila[0] = proc.getTipo();
                fila[1] = formatearMatematicasHTML(proc.getFuncion());
                fila[2] = proc.getVariable();
                fila[3] = (proc.getLimiteInferior() != null && !proc.getLimiteInferior().isEmpty()) ? proc.getLimiteInferior() : "—";
                fila[4] = (proc.getLimiteSuperior() != null && !proc.getLimiteSuperior().isEmpty()) ? proc.getLimiteSuperior() : "—";
                
                // Aplicamos un color azul al resultado final
                fila[5] = "<html><span style='color: #3071C4; font-family: Cambria Math; font-size: 15px;'>" 
                        + proc.getResultado().replace("^", "<sup>") 
                        + (proc.getResultado().contains("^") ? "</sup>" : "") 
                        + "</span></html>";
                
                modelo.addRow(fila);
            }
        }
        // Actualizar la etiqueta superior con la cantidad real de registros
        if (this.listaActual != null) {
            lblConteoRegistros.setText("   " + this.listaActual.size() + " registros   ");
        }
        tblResultados.setModel(modelo);
        
        // Centramos el texto en todas las columnas
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < tblResultados.getColumnCount(); i++) {
            tblResultados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Activar el filtro dinámico en la tabla
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        tblResultados.setRowSorter(sorter);
        
        if (this.listaActual != null) {
            lblConteoRegistros.setText("   " + this.listaActual.size() + " registros   ");
        }
    }
    
   private void filtrarTabla() {
        if (sorter == null) return;
        
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();
        String filtroTipo = cbxFiltro.getSelectedItem() != null ? cbxFiltro.getSelectedItem().toString() : "";

        java.util.List<javax.swing.RowFilter<Object,Object>> filtros = new java.util.ArrayList<>();

        // 1. Filtrar por tipo exacto (Combobox)
        if (!filtroTipo.equals("Todos") && !filtroTipo.isEmpty()) {
            filtros.add(javax.swing.RowFilter.regexFilter("(?i)^" + java.util.regex.Pattern.quote(filtroTipo) + "$", 0));
        }

        // 2. Filtro personalizado tolerante a HTML y matemáticas
        if (!textoBusqueda.isEmpty()) {
            filtros.add(new javax.swing.RowFilter<Object, Object>() {
                @Override
                public boolean include(javax.swing.RowFilter.Entry<? extends Object, ? extends Object> entry) {
                    // Extraemos el texto crudo de las columnas Función (1) y Resultado (5)
                    String funcionRaw = entry.getStringValue(1).toLowerCase();
                    String resultadoRaw = entry.getStringValue(5).toLowerCase();
                    
                    // Reconstruimos "<sup>2</sup>" a "^2", y luego borramos las etiquetas <html> y <span>
                    String funcionPlana = funcionRaw.replace("<sup>", "^").replaceAll("<[^>]+>", "");
                    String resultadoPlano = resultadoRaw.replace("<sup>", "^").replaceAll("<[^>]+>", "");
                    
                    // Comparamos el texto limpio con lo que el usuario escribió
                    return funcionPlana.contains(textoBusqueda) || resultadoPlano.contains(textoBusqueda);
                }
            });
        }

        // 3. Aplicar los filtros (Aquí reparamos el bug de "Todos los tipos")
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null); // Si no hay filtros, obligamos a mostrar toda la tabla
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.andFilter(filtros));
        }
        
        // Actualizar la píldora superior con la cantidad de registros que quedaron visibles
        lblConteoRegistros.setText("   " + sorter.getViewRowCount() + " registros   ");
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTituloCalculos = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblConteoRegistros = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        cbxFiltro = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));
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
        lblTituloApp.setText("Historial de Calculos");
        lblTituloApp.setOpaque(true);
        pnlHeader.add(lblTituloApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

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

        jPanel1.setBackground(new java.awt.Color(245, 247, 252));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 550));

        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tipo", "Funcion", "Variable", "Limite Inferior", "Limite Superior", "Resultado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblResultados);

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(this::btnRegresarActionPerformed);

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        lblTituloCalculos.setText("Tus calculos");

        lblSubtitulo.setText("Consulta, revisa o administra las operaciones que has realizado.");

        lblConteoRegistros.setText("0 registros");
        lblConteoRegistros.setAlignmentX(930.0F);
        lblConteoRegistros.setAlignmentY(90.0F);

        txtBuscar.addActionListener(this::txtBuscarActionPerformed);

        cbxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Derivada", "Integral Definida", "Integral Indefinida" }));
        cbxFiltro.addActionListener(this::cbxFiltroActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addGap(64, 64, 64))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(cbxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubtitulo)
                            .addComponent(lblTituloCalculos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblConteoRegistros)
                        .addGap(105, 105, 105))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblTituloCalculos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSubtitulo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(lblConteoRegistros)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(65, 65, 65))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tblResultados.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un registro de la tabla para editar.",
                    "Ninguna fila seleccionada",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; 
        }

        Procedimiento procAEditar = this.listaActual.get(filaSeleccionada);
        VentanaCalculadora calc = new VentanaCalculadora(this.usuarioActual);
        calc.cargarProcedimientoParaEditar(procAEditar); 
        calc.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblResultados.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un registro de la tabla para eliminar.",
                    "Ninguna fila seleccionada",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return; 
        }

        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este cálculo del historial?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            try {
                int idEliminar = this.listaActual.get(filaSeleccionada).getId(); 
                ProcedimientoDAO dao = new ProcedimientoDAO();
                dao.eliminarProcedimiento(idEliminar); 

                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Registro eliminado con éxito.", 
                        "Eliminado", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                cargarHistorial(); // Refresca la tabla automáticamente

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ocurrió un error al intentar eliminar el registro:\n" + e.getMessage(),
                        "Error de Base de Datos",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new VentanaCalculadora(this.usuarioActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cbxFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFiltroActionPerformed
        filtrarTabla();
        
    }//GEN-LAST:event_cbxFiltroActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VentanaHistorial().setVisible(true));
    }
    public VentanaHistorial() {
        initComponents();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxFiltro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConteoRegistros;
    private javax.swing.JLabel lblInicial;
    private javax.swing.JLabel lblIntegraHeader;
    private javax.swing.JLabel lblLogo2;
    private javax.swing.JLabel lblLogoHeader;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTituloApp;
    private javax.swing.JLabel lblTituloCalculos;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSesion;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
