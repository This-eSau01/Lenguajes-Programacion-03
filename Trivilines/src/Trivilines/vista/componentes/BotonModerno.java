package Trivilines.vista.componentes;

import Trivilines.util.ColoresUI;
import Trivilines.util.Constantes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonModerno extends JButton {

    public BotonModerno(String texto, Color colorFondo) {
        super(texto);
        setFont(Constantes.FONT_BOTON); // Asegurate que FONT_BOTON exista en Constantes
        setBackground(colorFondo);
        setForeground(Color.WHITE);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorFondo.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorFondo);
            }
        });
    }
}