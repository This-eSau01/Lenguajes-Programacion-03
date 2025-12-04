package Trivilines.vista.componentes;
import javax.swing.*;
import java.awt.*;

public class TarjetaCategoria extends JPanel {

    private JLabel lblNombre;

    public TarjetaCategoria(String nombre) {
        inicializar(nombre);
    }

    private void inicializar(String nombre) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        setBackground(Color.WHITE);

        lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD, 14f));

        add(lblNombre, BorderLayout.CENTER);
    }
}
