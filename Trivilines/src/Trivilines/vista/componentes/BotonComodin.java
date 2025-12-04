package Trivilines.vista.componentes;


import javax.swing.*;
import java.awt.*;

public class BotonComodin extends JButton {

    public BotonComodin(String texto) {
        super(texto);
        inicializar();
    }

    private void inicializar() {
        setFocusPainted(false);
        setMargin(new Insets(5, 5, 5, 5));
    }
}
