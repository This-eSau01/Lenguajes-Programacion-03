package Examen.Main;

import javax.swing.SwingUtilities;

import Examen.ControladorInventario;
import Examen.Vista.VentanaPrincipal;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorInventario controlador = new ControladorInventario();
            VentanaPrincipal v = new VentanaPrincipal(controlador);
            v.setVisible(true);
        });
    }
}
	