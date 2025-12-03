package Trivilines.vista.componentes;

import Trivilines.modelo.Pregunta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PanelPregunta extends JPanel {

    private JLabel lblTextoPregunta;
    private List<BotonRespuesta> botones;
    private Consumer<Integer> respuestaListener;

    public PanelPregunta() {
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Pregunta"));

        lblTextoPregunta = new JLabel("Pregunta actual", SwingConstants.CENTER);
        lblTextoPregunta.setVerticalAlignment(SwingConstants.TOP);
        lblTextoPregunta.setFont(lblTextoPregunta.getFont().deriveFont(Font.PLAIN, 18f));
        add(lblTextoPregunta, BorderLayout.NORTH);

        JPanel panelRespuestas = new JPanel(new GridLayout(2, 2, 10, 10));
        botones = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            BotonRespuesta btn = new BotonRespuesta(i);
            int idx = i;
            btn.addActionListener(e -> {
                if (respuestaListener != null && btn.isEnabled()) {
                    respuestaListener.accept(idx);
                }
            });
            botones.add(btn);
            panelRespuestas.add(btn);
        }

        add(panelRespuestas, BorderLayout.CENTER);
    }

    public void mostrarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            lblTextoPregunta.setText("Sin pregunta");
            for (BotonRespuesta b : botones) {
                b.setText("-");
                b.setEnabled(false);
            }
            return;
        }

        lblTextoPregunta.setText("<html>" + pregunta.getTexto() + "</html>");
        for (int i = 0; i < botones.size(); i++) {
            String texto = i < pregunta.getOpciones().size()
                    ? pregunta.getOpciones().get(i)
                    : "-";
            botones.get(i).setText(texto);
            botones.get(i).resetearEstadoVisual();
            botones.get(i).setEnabled(true);
        }
    }

    public void setRespuestaListener(Consumer<Integer> listener) {
        this.respuestaListener = listener;
    }

    public void marcarRespuestaCorrecta(int indice) {
        if (indice >= 0 && indice < botones.size()) {
            botones.get(indice).marcarCorrecta();
        }
    }

    public void marcarRespuestaIncorrecta(int indice) {
        if (indice >= 0 && indice < botones.size()) {
            botones.get(indice).marcarIncorrecta();
        }
    }

    public void limpiarResaltado() {
        for (BotonRespuesta b : botones) {
            b.resetearEstadoVisual();
        }
    }

    public void habilitarRespuestas(boolean habilitar) {
        for (BotonRespuesta b : botones) {
            b.setEnabled(habilitar);
        }
    }

    public void eliminarOpciones(List<Integer> indicesEliminar) {
        for (Integer idx : indicesEliminar) {
            if (idx >= 0 && idx < botones.size()) {
                botones.get(idx).setEnabled(false);
                botones.get(idx).setText("X");
            }
        }
    }
}
