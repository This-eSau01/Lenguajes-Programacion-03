package Trivilines.vista.componentes;

import javax.swing.*;
import java.awt.*;

public class BotonRespuesta extends JButton {

    private final int indice;

    public BotonRespuesta(int indice) {
        super("Opción " + (indice + 1));
        this.indice = indice;
        inicializar();
    }

    private void inicializar() {
        setFocusPainted(false);
        setMargin(new Insets(10, 10, 10, 10));
    }

    public int getIndice() {
        return indice;
    }

    public void marcarCorrecta() {
        setBackground(new Color(144, 238, 144)); // verde claro
        setOpaque(true);
    }

    public void marcarIncorrecta() {
        setBackground(new Color(255, 160, 122)); // rojo claro
        setOpaque(true);
    }

    public void resetearEstadoVisual() {
        setBackground(null);
        setOpaque(false);
    }
}
