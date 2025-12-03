package Trivilines.servicio;

import Trivilines.modelo.Comodin;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;

import java.util.List;

public interface ServicioComodines {

    /**
     * Asigna los comodines iniciales al jugador.
     */
    void asignarComodinesIniciales(Jugador jugador);

    /**
     * Devuelve la lista de comodines del jugador.
     */
    List<Comodin> obtenerComodinesJugador(Jugador jugador);

    /**
     * Intenta usar un comodín para el jugador.
     *
     * @return true si se pudo usar, false si no estaba disponible.
     */
    boolean usarComodin(Jugador jugador, TipoComodin tipo);

    /**
     * Para el comodín 50-50: devuelve los índices de opciones que deberían
     * ser eliminadas (incorrectas).
     */
    List<Integer> obtenerIndicesAEliminar5050(Pregunta pregunta);

    /**
     * Para el comodín Bomba: devuelve el multiplicador de puntaje que se
     * aplicaría (por ejemplo 2x).
     */
    int obtenerMultiplicadorPuntajeBomba();

    /**
     * Para el comodín Tiempo Extra: devuelve los segundos adicionales
     * sugeridos.
     */
    int obtenerSegundosExtraTiempo();
}
