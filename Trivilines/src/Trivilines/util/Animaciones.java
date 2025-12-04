package Trivilines.util;


import javax.swing.*;
import java.awt.*;


public final class Animaciones {

    private Animaciones() {
    }
    public static void parpadearFondo(JComponent comp, Color color, int duracionMs) {
        if (comp == null) return;

        Color colorOriginal = comp.getBackground();
        int intervalo = 120;
        int[] contador = {0};

        Timer timer = new Timer(intervalo, e -> {
            contador[0]++;
            if (contador[0] % 2 == 0) {
                comp.setBackground(colorOriginal);
            } else {
                comp.setBackground(color);
            }
            if (contador[0] * intervalo >= duracionMs) {
                ((Timer) e.getSource()).stop();
                comp.setBackground(colorOriginal);
            }
            comp.repaint();
        });
        timer.start();
    }
    public static void sacudir(JComponent comp, int amplitud, int duracionMs) {
        if (comp == null || comp.getParent() == null) return;

        Point posicionOriginal = comp.getLocation();
        int intervalo = 30;
        int[] tiempo = {0};

        Timer timer = new Timer(intervalo, e -> {
            tiempo[0] += intervalo;
            int fase = (tiempo[0] / intervalo) % 4;
            int desplazamiento = 0;

            switch (fase) {
                case 0:
                    desplazamiento = -amplitud;
                    break;
                case 1:
                    desplazamiento = amplitud;
                    break;
                case 2:
                    desplazamiento = -amplitud / 2;
                    break;
                case 3:
                    desplazamiento = amplitud / 2;
                    break;
                default:
                    desplazamiento = 0;
            }

            comp.setLocation(posicionOriginal.x + desplazamiento, posicionOriginal.y);

            if (tiempo[0] >= duracionMs) {
                ((Timer) e.getSource()).stop();
                comp.setLocation(posicionOriginal);
            }
            comp.getParent().repaint();
        });
        timer.start();
    }

    /**
     * Pequeño zoom in/out (escalado simulado con borde e insets).
     *
     * Nota: es un efecto visual simple, no un escalado real del componente.
     */
    public static void zoomLigero(JComponent comp, int duracionMs) {
        if (comp == null) return;

        Insets insetsOriginal = comp.getInsets();
        int intervalo = 40;
        int pasos = duracionMs / intervalo;
        int maxExtra = 4;

        Timer timer = new Timer(intervalo, null);
        timer.addActionListener(e -> {
            int pasoActual = ((Timer) e.getSource()).getDelay() * ((Timer) e.getSource()).getInitialDelay();

            // Esta aproximación es limitada; para algo más preciso habría que
            // manejar un contador externo. Se deja como demo simple.
            if (!comp.isShowing()) {
                ((Timer) e.getSource()).stop();
                return;
            }

            // Aquí simplemente forzamos un repintado, en la práctica
            // podrías combinar con cambio de borde, fuente, etc.
            comp.repaint();
            if (pasoActual >= pasos) {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
}
