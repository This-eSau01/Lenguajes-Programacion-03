package Trivilines.vista;

import Trivilines.controlador.ControladorComodines;
import Trivilines.controlador.ControladorJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.util.ColoresUI;
import Trivilines.vista.componentes.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelJuego extends JPanel {

    private final ControladorJuego controladorJuego;
    private final ControladorComodines controladorComodines;
    private final Runnable accionVolverMenu;

    private JLabel lblJugador;
    private JLabel lblPuntaje;
    private JLabel lblNumeroPregunta;

    private JLabel lblTextoPregunta;      // sección SOLO para la pregunta
    private BarraProgreso barraProgreso;  // barra debajo del texto

    private PanelCategorias panelCategorias;
    private PanelPregunta panelPregunta;   // solo respuestas
    private PanelComodines panelComodines;
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
        setBackground(ColoresUI.FONDO_ABAJO);

        // Norte: info jugador/puntaje/pregunta
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(ColoresUI.FONDO_ARRIBA);

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setOpaque(false);

        lblJugador = new JLabel("Jugador: -");
        lblPuntaje = new JLabel("Puntaje: 0");
        lblNumeroPregunta = new JLabel("Pregunta: 0");

        lblJugador.setForeground(ColoresUI.TEXTO_BLANCO);
        lblPuntaje.setForeground(ColoresUI.TEXTO_BLANCO);
        lblNumeroPregunta.setForeground(ColoresUI.TEXTO_BLANCO);

        panelInfo.add(lblJugador);
        panelInfo.add(Box.createHorizontalStrut(20));
        panelInfo.add(lblPuntaje);
        panelInfo.add(Box.createHorizontalStrut(20));
        panelInfo.add(lblNumeroPregunta);

        panelSuperior.add(panelInfo, BorderLayout.WEST);
        add(panelSuperior, BorderLayout.NORTH);

        // Centro: categorías (izq) + seccionPregunta (centro) + ruleta (der)
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(ColoresUI.FONDO_ABAJO);

        panelCategorias = new PanelCategorias();
        panelCategorias.setPreferredSize(new Dimension(220, 0));
        panelCategorias.setBackground(ColoresUI.FONDO_ABAJO);
        panelCentro.add(panelCategorias, BorderLayout.WEST);

        // Sección SOLO para pregunta + barra + respuestas
        JPanel seccionPregunta = new JPanel(new BorderLayout(5, 5));
        seccionPregunta.setBorder(BorderFactory.createTitledBorder("Pregunta"));
        seccionPregunta.setBackground(ColoresUI.COLOR_TARJETA);

        lblTextoPregunta = new JLabel("Pregunta actual", SwingConstants.CENTER);
        lblTextoPregunta.setVerticalAlignment(SwingConstants.TOP);
        lblTextoPregunta.setFont(lblTextoPregunta.getFont().deriveFont(Font.BOLD, 20f));
        lblTextoPregunta.setForeground(ColoresUI.TEXTO_BLANCO);

        barraProgreso = new BarraProgreso();

        JPanel panelEnunciado = new JPanel(new BorderLayout());
        panelEnunciado.setOpaque(false);
        panelEnunciado.add(lblTextoPregunta, BorderLayout.CENTER);
        panelEnunciado.add(barraProgreso, BorderLayout.SOUTH);

        seccionPregunta.add(panelEnunciado, BorderLayout.NORTH);

        panelPregunta = new PanelPregunta();
        seccionPregunta.add(panelPregunta, BorderLayout.CENTER);

        panelCentro.add(seccionPregunta, BorderLayout.CENTER);

        animacionRuleta = new AnimacionRuleta();
        animacionRuleta.setPreferredSize(new Dimension(200, 200));
        animacionRuleta.setBackground(ColoresUI.FONDO_ABAJO);
        panelCentro.add(animacionRuleta, BorderLayout.EAST);

        add(panelCentro, BorderLayout.CENTER);

        // Sur: comodines + botones
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBackground(ColoresUI.FONDO_ABAJO);

        panelComodines = new PanelComodines();
        panelComodines.setBackground(ColoresUI.FONDO_ABAJO);
        panelInferior.add(panelComodines, BorderLayout.WEST);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        btnSiguiente = crearBotonAccion("Siguiente");
        btnVolverMenu = crearBotonAccion("Volver al menú");

        panelBotones.add(btnSiguiente);
        panelBotones.add(btnVolverMenu);
        panelInferior.add(panelBotones, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);

        // Listeners
        panelPregunta.setRespuestaListener(this::manejarRespuesta);
        configurarComodines();

        btnSiguiente.addActionListener(e -> cargarSiguientePregunta());
        btnVolverMenu.addActionListener(e -> accionVolverMenu.run());
    }

    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(ColoresUI.BTN_GENERICO);
        btn.setForeground(ColoresUI.TEXTO_BLANCO);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ColoresUI.BTN_GENERICO_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ColoresUI.BTN_GENERICO);
            }
        });

        return btn;
    }

    public void iniciarPartida(Pregunta primeraPregunta) {
        Jugador jugador = controladorJuego.getJugadorActual();
        if (jugador != null) {
            lblJugador.setText("Jugador: " + jugador.getNombre());
        }
        lblPuntaje.setText("Puntaje: " + controladorJuego.getPuntajeActual());
        lblNumeroPregunta.setText("Pregunta: " + controladorJuego.getNumeroPreguntaActual());

        dobleOportunidadActiva = false;
        panelPregunta.habilitarRespuestas(true);
        panelComodines.resetear();
        barraProgreso.resetear();

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
        if (pregunta == null) return;

        String htmlPregunta = "<html><div style='text-align:center;'>" +
                pregunta.getTexto() +
                "</div></html>";
        lblTextoPregunta.setText(htmlPregunta);

        panelPregunta.mostrarPregunta(pregunta);
        lblNumeroPregunta.setText("Pregunta: " + controladorJuego.getNumeroPreguntaActual());

        barraProgreso.resetear();
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
                    barraProgreso.resetear();
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
