package Trivilines.controlador;

import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;
import Trivilines.servicio.ServicioAudio;
import Trivilines.servicio.ServicioComodines;

import java.util.Collections;
import java.util.List;

/**
 * Controla el uso concreto de cada tipo de comodín.
 *
 * La vista (por ejemplo PanelComodines) usará este controlador para:
 * - Activar comodín 50-50 y saber qué opciones ocultar.
 * - Activar Tiempo Extra y saber cuántos segundos añadir.
 * - Activar Bomba para aplicar multiplicador de puntos (la vista/controlador
 *   del juego decide cómo usar ese multiplicador).
 * - Activar Doble Oportunidad (la vista puede usar un flag para permitir
 *   un segundo intento).
 */
public class ControladorComodines {

    private final ServicioComodines servicioComodines;
    private final ServicioAudio servicioAudio;

    public ControladorComodines(ServicioComodines servicioComodines,
                                ServicioAudio servicioAudio) {
        this.servicioComodines = servicioComodines;
        this.servicioAudio = servicioAudio;
    }

    /**
     * Usa el comodín 50-50 para una pregunta.
     *
     * @return lista de índices de opciones que deben eliminarse (incorrectas).
     *         Si no se pudo usar el comodín, devuelve lista vacía.
     */
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

    /**
     * Activa el comodín Doble Oportunidad.
     *
     * @return true si se pudo activar, false si ya no estaba disponible.
     *
     * La forma de usar este comodín es decisión de la vista / lógica de juego:
     * normalmente se permitiría un segundo intento si el primero fue incorrecto.
     */
    public boolean usarComodinDobleOportunidad(Jugador jugador) {
        boolean usado = servicioComodines.usarComodin(jugador, TipoComodin.DOBLE_OPORTUNIDAD);
        if (usado) {
            servicioAudio.reproducirSonidoComodin();
        }
        return usado;
    }
}
