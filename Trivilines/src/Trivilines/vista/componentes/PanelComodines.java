package Trivilines.vista.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelComodines extends JPanel {

    private BotonComodin btn5050;
    private BotonComodin btnDobleOportunidad;
    private BotonComodin btnBomba;
    private BotonComodin btnTiempoExtra;

    private Runnable accion5050;
    private Runnable accionDobleOportunidad;
    private Runnable accionBomba;
    private Runnable accionTiempoExtra;

    public PanelComodines() {
        inicializar();
    }

    private void inicializar() {
        setLayout(new GridLayout(1, 4, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Comodines"));

        btn5050 = new BotonComodin("50-50");
        btnDobleOportunidad = new BotonComodin("Doble");
        btnBomba = new BotonComodin("Bomba");
        btnTiempoExtra = new BotonComodin("Tiempo");

        add(btn5050);
        add(btnDobleOportunidad);
        add(btnBomba);
        add(btnTiempoExtra);

        btn5050.addActionListener(e -> {
            if (accion5050 != null && btn5050.isEnabled()) {
                accion5050.run();
                btn5050.setEnabled(false);
            }
        });

        btnDobleOportunidad.addActionListener(e -> {
            if (accionDobleOportunidad != null && btnDobleOportunidad.isEnabled()) {
                accionDobleOportunidad.run();
                btnDobleOportunidad.setEnabled(false);
            }
        });

        btnBomba.addActionListener(e -> {
            if (accionBomba != null && btnBomba.isEnabled()) {
                accionBomba.run();
                btnBomba.setEnabled(false);
            }
        });

        btnTiempoExtra.addActionListener(e -> {
            if (accionTiempoExtra != null && btnTiempoExtra.isEnabled()) {
                accionTiempoExtra.run();
                btnTiempoExtra.setEnabled(false);
            }
        });
    }

    public void setAccion5050(Runnable accion5050) {
        this.accion5050 = accion5050;
    }

    public void setAccionDobleOportunidad(Runnable accionDobleOportunidad) {
        this.accionDobleOportunidad = accionDobleOportunidad;
    }

    public void setAccionBomba(Runnable accionBomba) {
        this.accionBomba = accionBomba;
    }

    public void setAccionTiempoExtra(Runnable accionTiempoExtra) {
        this.accionTiempoExtra = accionTiempoExtra;
    }

    public void resetear() {
        btn5050.setEnabled(true);
        btnDobleOportunidad.setEnabled(true);
        btnBomba.setEnabled(true);
        btnTiempoExtra.setEnabled(true);
    }
}
