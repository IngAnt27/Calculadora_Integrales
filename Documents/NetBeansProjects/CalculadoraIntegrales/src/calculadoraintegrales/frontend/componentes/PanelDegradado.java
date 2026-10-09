package calculadoraintegrales.frontend.componentes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
import javax.swing.JPanel;
import java.awt.*;

public class PanelDegradado extends JPanel {



    private Color colorInicio;
    private Color colorFin;

    // Constructor por defecto (mantiene compatibilidad con el login)
    public PanelDegradado() {
        this(new Color(0x0C122B), new Color(0x16204A));
    }

    // Constructor nuevo, para personalizar tonos por ventana
    public PanelDegradado(Color colorInicio, Color colorFin) {
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        GradientPaint degradado = new GradientPaint(0, 0, colorInicio, ancho, alto, colorFin);
        g2.setPaint(degradado);
        g2.fillRect(0, 0, ancho, alto);

        g2.setColor(new Color(255, 255, 255, 40));
        int espacio = 28;
        for (int x = 20; x < ancho; x += espacio) {
            for (int y = 20; y < alto; y += espacio) {
                g2.fillOval(x, y, 2, 2);
            }
        }
    }
}