package Trivilines.vista.componentes;

import Trivilines.modelo.Pregunta;
import Trivilines.util.ColoresUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PanelPregunta extends JPanel {

    private List<BotonRespuesta> botones;
    private Consumer<Integer> respuestaListener;

    public PanelPregunta() {
        inicializar();
    }

    private void inicializar() {
        setLayout(new GridLayout(2, 2, 10, 10));
        setOpaque(false);

        botones = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BotonRespuesta btn = new BotonRespuesta(i);
            switch (i) {
                case 0 -> btn.setColorBase(ColoresUI.BTN_AZUL);
                case 1 -> btn.setColorBase(ColoresUI.BTN_ROJO);
                case 2 -> btn.setColorBase(ColoresUI.BTN_AMARILLO);
                case 3 -> btn.setColorBase(ColoresUI.BTN_VERDE);
            }
            int idx = i;
            btn.addActionListener(e -> {
                if (respuestaListener != null && btn.isEnabled()) {
                    respuestaListener.accept(idx);
                }
            });
            botones.add(btn);
            add(btn);
        }
    }

    /** SOLO actualiza las opciones; el texto de la pregunta se maneja en PanelJuego */
    public void mostrarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            for (BotonRespuesta b : botones) {
                b.setText("-");
                b.setEnabled(false);
            }
            return;
        }

        for (int i = 0; i < botones.size(); i++) {
            String texto = i < pregunta.getOpciones().size()
                    ? pregunta.getOpciones().get(i)
                    : "-";
            String htmlOpcion = "<html><center>" + texto + "</center></html>";
            BotonRespuesta b = botones.get(i);
            b.setText(htmlOpcion);
            b.resetearEstadoVisual();
            b.setEnabled(true);
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
                BotonRespuesta b = botones.get(idx);
                b.setEnabled(false);
                b.setText("✖");
            }
        }
    }
}
