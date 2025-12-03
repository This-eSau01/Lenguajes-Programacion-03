package Trivilines.vista;


import Trivilines.controlador.ControladorJuego;
import Trivilines.modelo.Jugador;

import javax.swing.*;
import java.awt.*;

public class DialogoNuevoJugador extends JDialog {

    private final ControladorJuego controladorJuego;

    private JTextField txtNombre;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboNivel;
    private boolean aceptado;

    private int categoriaSeleccionadaId = 1;
    private int nivelSeleccionado = 1;

    public DialogoNuevoJugador(Frame owner,
                               boolean modal,
                               ControladorJuego controladorJuego) {
        super(owner, "Nuevo jugador y partida", modal);
        this.controladorJuego = controladorJuego;
        inicializar();
    }

    private void inicializar() {
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCampos.add(new JLabel("Nombre del jugador:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Categoría:"));
        comboCategoria = new JComboBox<>(new String[]{
                "1 - Ciencia",
                "2 - Arte",
                "3 - Deportes",
                "4 - Historia",
                "5 - Geografía",
                "6 - Entretenimiento"
        });
        panelCampos.add(comboCategoria);

        panelCampos.add(new JLabel("Nivel de dificultad:"));
        comboNivel = new JComboBox<>(new String[]{
                "1 - Fácil",
                "2 - Medio",
                "3 - Difícil"
        });
        panelCampos.add(comboNivel);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);

        add(panelBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> {
            aceptado = false;
            dispose();
        });

        btnAceptar.addActionListener(e -> aceptar());
    }

    private void aceptar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debes ingresar un nombre.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Jugador jugador = controladorJuego.seleccionarOcrearJugador(nombre);
            if (jugador == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo crear/seleccionar el jugador.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Categoría
        String catStr = (String) comboCategoria.getSelectedItem();
        if (catStr != null && catStr.contains("-")) {
            try {
                categoriaSeleccionadaId = Integer.parseInt(catStr.split("-")[0].trim());
            } catch (NumberFormatException e) {
                categoriaSeleccionadaId = 1;
            }
        }

        // Nivel
        String nivelStr = (String) comboNivel.getSelectedItem();
        if (nivelStr != null && nivelStr.contains("-")) {
            try {
                nivelSeleccionado = Integer.parseInt(nivelStr.split("-")[0].trim());
            } catch (NumberFormatException e) {
                nivelSeleccionado = 1;
            }
        }

        aceptado = true;
        dispose();
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public int getCategoriaSeleccionadaId() {
        return categoriaSeleccionadaId;
    }

    public int getNivelSeleccionado() {
        return nivelSeleccionado;
    }
}
