package Trivilines.vista;

import Trivilines.util.ColoresUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        // Fondo del panel (agradable y moderno)
        setBackground(ColoresUI.FONDO_ABAJO);

        // Panel superior con título
        JLabel lblTitulo = new JLabel("Juego de Trivia", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 32f));
        lblTitulo.setForeground(ColoresUI.TEXTO_BLANCO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central con botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 20, 20));
        panelBotones.setOpaque(false); // deja ver el color del fondo
        panelBotones.setBorder(BorderFactory.createEmptyBorder(60, 200, 60, 200));

        btnNuevoJuego = crearBotonMenu("Nuevo juego");
        btnEstadisticas = crearBotonMenu("Estadísticas");
        btnSalir = crearBotonMenu("Salir");

        panelBotones.add(btnNuevoJuego);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // Eventos
        btnNuevoJuego.addActionListener(e -> { if (listener != null) listener.onNuevoJuego(); });
        btnEstadisticas.addActionListener(e -> { if (listener != null) listener.onVerEstadisticas(); });
        btnSalir.addActionListener(e -> { if (listener != null) listener.onSalir(); });
    }


    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);

        btn.setBackground(ColoresUI.BTN_GENERICO);
        btn.setForeground(ColoresUI.TEXTO_BLANCO);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 20f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // Hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ColoresUI.BTN_GENERICO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ColoresUI.BTN_GENERICO);
            }
        });

        return btn;
    }

    public void setMenuListener(MenuListener listener) {
        this.listener = listener;
    }
}
