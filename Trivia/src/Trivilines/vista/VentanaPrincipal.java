package Trivilines.vista;


import Trivilines.controlador.ControladorComodines;
import Trivilines.controlador.ControladorJuego;
import Trivilines.modelo.Pregunta;


import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private static final String PANTALLA_MENU = "MENU";
    private static final String PANTALLA_JUEGO = "JUEGO";
    private static final String PANTALLA_ESTADISTICAS = "ESTADISTICAS";

    private final ControladorJuego controladorJuego;
    private final ControladorComodines controladorComodines;

    private CardLayout cardLayout;
    private JPanel panelCentral;

    private PanelMenu panelMenu;
    private PanelJuego panelJuego;
    private PanelEstadisticas panelEstadisticas;

    public VentanaPrincipal(ControladorJuego controladorJuego,
                            ControladorComodines controladorComodines) {
        this.controladorJuego = controladorJuego;
        this.controladorComodines = controladorComodines;
        inicializar();
    }

    private void inicializar() {
        setTitle("Juego de Trivia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelMenu = new PanelMenu();
        panelJuego = new PanelJuego(controladorJuego, controladorComodines, this::mostrarMenu);
        panelEstadisticas = new PanelEstadisticas(controladorJuego, this::mostrarMenu);

        panelMenu.setMenuListener(new PanelMenu.MenuListener() {
            @Override
            public void onNuevoJuego() {
                abrirDialogoNuevoJugador();
            }

            @Override
            public void onVerEstadisticas() {
                panelEstadisticas.actualizarEstadisticas();
                mostrarPantalla(PANTALLA_ESTADISTICAS);
            }

            @Override
            public void onSalir() {
                dispose();
            }
        });

        panelCentral.add(panelMenu, PANTALLA_MENU);
        panelCentral.add(panelJuego, PANTALLA_JUEGO);
        panelCentral.add(panelEstadisticas, PANTALLA_ESTADISTICAS);

        add(panelCentral, BorderLayout.CENTER);
    }

    private void abrirDialogoNuevoJugador() {
        DialogoNuevoJugador dialogo = new DialogoNuevoJugador(this, true, controladorJuego);
        dialogo.setVisible(true);

        if (dialogo.isAceptado()) {
            int categoriaId = dialogo.getCategoriaSeleccionadaId();
            int nivel = dialogo.getNivelSeleccionado();
            Pregunta primera = controladorJuego.iniciarNuevaPartida(categoriaId, nivel);
            panelJuego.iniciarPartida(primera);
            mostrarPantalla(PANTALLA_JUEGO);
        }
    }

    public void mostrarPantalla(String nombrePantalla) {
        cardLayout.show(panelCentral, nombrePantalla);
    }

    public void mostrarMenu() {
        mostrarPantalla(PANTALLA_MENU);
    }
}
