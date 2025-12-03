package Trivilines.vista.componentes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Componente simple que dibuja una ruleta y la rota con un Timer
 * cuando se llama a iniciarAnimacion().
 */
public class AnimacionRuleta extends JPanel {

    private BufferedImage imagenRuleta;
    private double angulo;
    private Timer timer;

    public AnimacionRuleta() {
        cargarImagen();
        inicializarTimer();
    }

    private void cargarImagen() {
        try {
            imagenRuleta = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("imagenes/ruleta.png"));
        } catch (IOException | IllegalArgumentException e) {
            imagenRuleta = null;
            System.err.println("No se pudo cargar la imagen de la ruleta: " + e.getMessage());
        }
    }

    private void inicializarTimer() {
        timer = new Timer(40, e -> {
            angulo += Math.toRadians(10);
            repaint();
        });
    }

    public void iniciarAnimacion() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void detenerAnimacion() {
        if (timer.isRunning()) {
            timer.stop();
        }
        angulo = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenRuleta == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        int imgW = imagenRuleta.getWidth();
        int imgH = imagenRuleta.getHeight();

        double escala = Math.min((double) w / imgW, (double) h / imgH);

        AffineTransform at = new AffineTransform();
        at.translate(w / 2.0, h / 2.0);
        at.rotate(angulo);
        at.scale(escala, escala);
        at.translate(-imgW / 2.0, -imgH / 2.0);

        g2.drawImage(imagenRuleta, at, null);
        g2.dispose();
    }
}
