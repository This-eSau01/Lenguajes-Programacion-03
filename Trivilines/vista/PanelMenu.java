package Trivilines.vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    public interface MenuListener {
        void onNuevoJuego();
        void onVerEstadisticas();
        void onSalir();
    }

    private MenuListener listener;

    private JButton btnNuevoJuego;
    private JButton btnEstadisticas;
    private JButton btnSalir;

    public PanelMenu() {
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout());

        // Panel superior con logo
        JLabel lblTitulo = new JLabel("Juego de Trivia", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 32f));
        add(lblTitulo, BorderLayout.NORTH);

        // Centro con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));

        btnNuevoJuego = new JButton("Nuevo juego");
        btnEstadisticas = new JButton("Estadísticas");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnNuevoJuego);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // Listeners
        btnNuevoJuego.addActionListener(e -> {
            if (listener != null) listener.onNuevoJuego();
        });

        btnEstadisticas.addActionListener(e -> {
            if (listener != null) listener.onVerEstadisticas();
        });

        btnSalir.addActionListener(e -> {
            if (listener != null) listener.onSalir();
        });
    }

    public void setMenuListener(MenuListener listener) {
        this.listener = listener;
    }
}
