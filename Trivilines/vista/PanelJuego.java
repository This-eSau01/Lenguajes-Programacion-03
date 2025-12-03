package Trivilines.vista;

import Trivilines.controlador.ControladorComodines;
import Trivilines.controlador.ControladorJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;
import Trivilines.vista.componentes.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class PanelJuego extends JPanel {

    private final ControladorJuego controladorJuego;
    private final ControladorComodines controladorComodines;
    private final Runnable accionVolverMenu;

    private JLabel lblJugador;
    private JLabel lblPuntaje;
    private JLabel lblNumeroPregunta;

    private PanelCategorias panelCategorias;
    private PanelPregunta panelPregunta;
    private PanelComodines panelComodines;
    private BarraProgreso barraProgreso;
    private AnimacionRuleta animacionRuleta;

    private JButton btnSiguiente;
    private JButton btnVolverMenu;

    private boolean dobleOportunidadActiva;

    public PanelJuego(ControladorJuego controladorJuego,
                      ControladorComodines controladorComodines,
                      Runnable accionVolverMenu) {
        this.controladorJuego = controladorJuego;
        this.controladorComodines = controladorComodines;
        this.accionVolverMenu = accionVolverMenu;
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Norte: info de jugador / puntaje
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblJugador = new JLabel("Jugador: -");
        lblPuntaje = new JLabel("Puntaje: 0");
        lblNumeroPregunta = new JLabel("Pregunta: 0");

        panelInfo.add(lblJugador);
        panelInfo.add(Box.createHorizontalStrut(20));
        panelInfo.add(lblPuntaje);
        panelInfo.add(Box.createHorizontalStrut(20));
        panelInfo.add(lblNumeroPregunta);

        panelSuperior.add(panelInfo, BorderLayout.WEST);

        barraProgreso = new BarraProgreso();
        barraProgreso.setPreferredSize(new Dimension(200, 20));
        panelSuperior.add(barraProgreso, BorderLayout.EAST);

        add(panelSuperior, BorderLayout.NORTH);

        // Centro: categorías, pregunta, animación
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));

        panelCategorias = new PanelCategorias();
        panelCategorias.setPreferredSize(new Dimension(220, 0));
        panelCentro.add(panelCategorias, BorderLayout.WEST);

        panelPregunta = new PanelPregunta();
        panelCentro.add(panelPregunta, BorderLayout.CENTER);

        animacionRuleta = new AnimacionRuleta();
        animacionRuleta.setPreferredSize(new Dimension(200, 200));
        panelCentro.add(animacionRuleta, BorderLayout.EAST);

        add(panelCentro, BorderLayout.CENTER);

        // Sur: comodines + botones acción
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        panelComodines = new PanelComodines();
        panelInferior.add(panelComodines, BorderLayout.WEST);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSiguiente = new JButton("Siguiente");
        btnVolverMenu = new JButton("Volver al menú");

        panelBotones.add(btnSiguiente);
        panelBotones.add(btnVolverMenu);
        panelInferior.add(panelBotones, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        // Listeners de respuestas
        panelPregunta.setRespuestaListener(this::manejarRespuesta);

        // Listeners de comodines
        configurarComodines();

        // Botones
        btnSiguiente.addActionListener(e -> cargarSiguientePregunta());
        btnVolverMenu.addActionListener(e -> {
            accionVolverMenu.run();
        });
    }

    public void iniciarPartida(Pregunta primeraPregunta) {
        Jugador jugador = controladorJuego.getJugadorActual();
        if (jugador != null) {
            lblJugador.setText("Jugador: " + jugador.getNombre());
        }
        lblPuntaje.setText("Puntaje: " + controladorJuego.getPuntajeActual());
        lblNumeroPregunta.setText("Pregunta: " + controladorJuego.getNumeroPreguntaActual());
        barraProgreso.resetear();

        dobleOportunidadActiva = false;
        panelPregunta.habilitarRespuestas(true);
        panelComodines.resetear();

        if (primeraPregunta != null) {
            mostrarPregunta(primeraPregunta);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No hay preguntas disponibles para la categoría/nivel seleccionados.",
                    "Sin preguntas",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarPregunta(Pregunta pregunta) {
        panelPregunta.mostrarPregunta(pregunta);
        lblNumeroPregunta.setText("Pregunta: " + controladorJuego.getNumeroPreguntaActual());
        barraProgreso.iniciar();
        animacionRuleta.detenerAnimacion();
    }

    private void manejarRespuesta(int indiceRespuesta) {
        Pregunta pregunta = controladorJuego.getPreguntaActual();
        if (pregunta == null) {
            return;
        }

        barraProgreso.detener();

        boolean correcta = controladorJuego.responderPregunta(indiceRespuesta);
        if (correcta) {
            panelPregunta.marcarRespuestaCorrecta(indiceRespuesta);
        } else {
            panelPregunta.marcarRespuestaIncorrecta(indiceRespuesta);
            panelPregunta.marcarRespuestaCorrecta(pregunta.getIndiceRespuestaCorrecta());

            if (dobleOportunidadActiva) {
                dobleOportunidadActiva = false;
                int opcion = JOptionPane.showConfirmDialog(this,
                        "Tienes doble oportunidad. ¿Quieres intentar de nuevo?",
                        "Doble oportunidad",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    panelPregunta.habilitarRespuestas(true);
                    panelPregunta.limpiarResaltado();
                    barraProgreso.iniciar();
                    return;
                }
            }
        }

        lblPuntaje.setText("Puntaje: " + controladorJuego.getPuntajeActual());
        panelPregunta.habilitarRespuestas(false);
    }

    private void cargarSiguientePregunta() {
        barraProgreso.detener();
        panelPregunta.limpiarResaltado();
        panelPregunta.habilitarRespuestas(true);
        Pregunta siguiente = controladorJuego.siguientePregunta();
        if (siguiente == null) {
            JOptionPane.showMessageDialog(this,
                    "Partida finalizada.\nPuntaje total: " + controladorJuego.getPuntajeActual(),
                    "Fin de partida",
                    JOptionPane.INFORMATION_MESSAGE);
            accionVolverMenu.run();
        } else {
            mostrarPregunta(siguiente);
        }
    }

    private void configurarComodines() {
        panelComodines.setAccion5050(() -> {
            Jugador jugador = controladorJuego.getJugadorActual();
            Pregunta pregunta = controladorJuego.getPreguntaActual();
            if (jugador == null || pregunta == null) return;

            List<Integer> indicesEliminar =
                    controladorComodines.usarComodin5050(jugador, pregunta);
            if (indicesEliminar.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No puedes usar este comodín en este momento.",
                        "Comodín 50-50",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            panelPregunta.eliminarOpciones(indicesEliminar);
        });

        panelComodines.setAccionTiempoExtra(() -> {
            Jugador jugador = controladorJuego.getJugadorActual();
            int segundos = controladorComodines.usarComodinTiempoExtra(jugador);
            if (segundos <= 0) {
                JOptionPane.showMessageDialog(this,
                        "No puedes usar este comodín en este momento.",
                        "Tiempo extra",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            barraProgreso.aumentarTiempo(segundos);
            JOptionPane.showMessageDialog(this,
                    "Se han añadido " + segundos + " segundos.",
                    "Tiempo extra",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panelComodines.setAccionBomba(() -> {
            Jugador jugador = controladorJuego.getJugadorActual();
            int multiplicador = controladorComodines.usarComodinBomba(jugador);
            if (multiplicador <= 1) {
                JOptionPane.showMessageDialog(this,
                        "No puedes usar este comodín en este momento.",
                        "Bomba",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this,
                    "La próxima pregunta tendrá un puntaje especial.",
                    "Bomba",
                    JOptionPane.INFORMATION_MESSAGE);
            animacionRuleta.iniciarAnimacion();
        });

        panelComodines.setAccionDobleOportunidad(() -> {
            Jugador jugador = controladorJuego.getJugadorActual();
            boolean activado = controladorComodines.usarComodinDobleOportunidad(jugador);
            if (!activado) {
                JOptionPane.showMessageDialog(this,
                        "No puedes usar este comodín en este momento.",
                        "Doble oportunidad",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dobleOportunidadActiva = true;
            JOptionPane.showMessageDialog(this,
                    "Doble oportunidad activada para esta pregunta.",
                    "Doble oportunidad",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
