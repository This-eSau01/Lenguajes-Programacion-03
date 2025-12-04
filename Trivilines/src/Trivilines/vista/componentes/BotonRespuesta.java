package Trivilines.vista.componentes;

import Trivilines.util.ColoresUI;

import javax.swing.*;
import java.awt.*;

public class BotonRespuesta extends JButton {

    private final int indice;
    private Color colorBase;

    // 🔹 NUEVO: Configuración de fuente
    private static final Font FUENTE_RESPUESTA =
            new Font("Arial", Font.BOLD, 22); // Cambia aquí la fuente y tamaño

    public BotonRespuesta(int indice) {
        super("Opción " + (indice + 1));
        this.indice = indice;
        this.colorBase = ColoresUI.BTN_AZUL;
        inicializar();
    }

    private void inicializar() {
        setFocusPainted(false);
        setMargin(new Insets(30, 30, 30, 30));  // Más espacio interno
        setBackground(colorBase);

        // 🔹 AQUI SE CAMBIA LA FUENTE Y EL TAMAÑO
        setFont(FUENTE_RESPUESTA);

        // 🔹 AQUI SE CAMBIA EL COLOR DEL TEXTO
        setForeground(Color.BLACK);

        setOpaque(true);
    }

    public void setColorBase(Color colorBase) {
        this.colorBase = colorBase;
        setBackground(colorBase);
        repaint();
    }

    public int getIndice() {
        return indice;
    }

    public void marcarCorrecta() {
        setBackground(ColoresUI.CORRECTO);
    }

    public void marcarIncorrecta() {
        setBackground(ColoresUI.INCORRECTO);
    }

    public void resetearEstadoVisual() {
        setBackground(colorBase);
    }
}
