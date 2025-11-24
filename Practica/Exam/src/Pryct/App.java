package Pryct;

import Pryct.controlador.ControladorInventario;
import Pryct.notificaciones.GestorNotificacionesInventario;
import Pryct.vista.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Si falla Nimbus, se queda el look and feel por defecto
        }

        GestorNotificacionesInventario gestor = new GestorNotificacionesInventario();
        ControladorInventario controlador = new ControladorInventario(gestor);

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vista = new VentanaPrincipal(controlador, gestor);
            vista.setVisible(true);
        });
    }
}
