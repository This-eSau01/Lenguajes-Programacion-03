package Trivilines.vista.componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Barra de progreso simple pensada como temporizador visual.
 * Por defecto cuenta de 0 a 100 en 20 segundos.
 */
public class BarraProgreso extends JProgressBar {

    private static final int TIEMPO_DEFECTO_SEGUNDOS = 20;

    private int tiempoTotalSegundos;
    private int valorActual;
    private Timer timer;

    public BarraProgreso() {
        super(0, 100);
        this.tiempoTotalSegundos = TIEMPO_DEFECTO_SEGUNDOS;
        setStringPainted(true);
        inicializarTimer();
    }

    private void inicializarTimer() {
        timer = new Timer(200, e -> {
            valorActual += 100 * 200 / (tiempoTotalSegundos * 1000);
            setValue(valorActual);
            if (valorActual >= 100) {
                detener();
            }
        });
    }

    public void iniciar() {
        resetear();
        timer.start();
    }

    public void detener() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public void resetear() {
        detener();
        valorActual = 0;
        setValue(0);
    }

    public void aumentarTiempo(int segundosExtra) {
        tiempoTotalSegundos += segundosExtra;
    }

    public void setTiempoTotalSegundos(int tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }
}
