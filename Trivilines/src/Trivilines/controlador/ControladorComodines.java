package Trivilines.controlador;

import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;
import Trivilines.servicio.ServicioAudio;
import Trivilines.servicio.ServicioComodines;

import java.util.Collections;
import java.util.List;


public class ControladorComodines {

    private final ServicioComodines servicioComodines;
    private final ServicioAudio servicioAudio;

    public ControladorComodines(ServicioComodines servicioComodines,
                                ServicioAudio servicioAudio) {
        this.servicioComodines = servicioComodines;
        this.servicioAudio = servicioAudio;
    }


    public List<Integer> usarComodin5050(Jugador jugador, Pregunta pregunta) {
        boolean usado = servicioComodines.usarComodin(jugador, TipoComodin.CINCUENTA_CINCUENTA);
        if (!usado) {
            return Collections.emptyList();
        }
        servicioAudio.reproducirSonidoComodin();
        return servicioComodines.obtenerIndicesAEliminar5050(pregunta);
    }

    /**
     * Activa el comodín Tiempo Extra.
     *
     * @return segundos adicionales a sumar al temporizador. Si no se pudo usar,
     *         devuelve 0.
     */
    public int usarComodinTiempoExtra(Jugador jugador) {
        boolean usado = servicioComodines.usarComodin(jugador, TipoComodin.TIEMPO_EXTRA);
        if (!usado) {
            return 0;
        }
        servicioAudio.reproducirSonidoComodin();
        return servicioComodines.obtenerSegundosExtraTiempo();
    }

    /**
     * Activa el comodín Bomba.
     *
     * @return multiplicador de puntaje que se debe aplicar a la siguiente
     *         pregunta (por ejemplo 2). Si no se pudo usar, devuelve 1.
     */
    public int usarComodinBomba(Jugador jugador) {
        boolean usado = servicioComodines.usarComodin(jugador, TipoComodin.BOMBA);
        if (!usado) {
            return 1;
        }
        servicioAudio.reproducirSonidoExplosion();
        return servicioComodines.obtenerMultiplicadorPuntajeBomba();
    }


    public boolean usarComodinDobleOportunidad(Jugador jugador) {
        boolean usado = servicioComodines.usarComodin(jugador, TipoComodin.DOBLE_OPORTUNIDAD);
        if (usado) {
            servicioAudio.reproducirSonidoComodin();
        }
        return usado;
    }
}