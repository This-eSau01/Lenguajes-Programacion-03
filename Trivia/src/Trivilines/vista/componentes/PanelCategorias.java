package Trivilines.vista.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelCategorias extends JPanel {

    public PanelCategorias() {
        inicializar();
    }

    private void inicializar() {
        setLayout(new GridLayout(6, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Categorías"));

        add(new TarjetaCategoria("Ciencia"));
        add(new TarjetaCategoria("Arte"));
        add(new TarjetaCategoria("Deportes"));
        add(new TarjetaCategoria("Historia"));
        add(new TarjetaCategoria("Geografía"));
        add(new TarjetaCategoria("Entretenimiento"));
    }
}
