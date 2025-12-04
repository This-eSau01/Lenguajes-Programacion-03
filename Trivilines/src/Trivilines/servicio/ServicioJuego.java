package Trivilines.servicio;

import Trivilines.modelo.EstadoJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;
import Trivilines.modelo.Pregunta;

public interface ServicioJuego {

    void iniciarNuevaPartida(Jugador jugador, int categoriaId, int nivel);

    /**
     * Devuelve la pregunta actual (puede ser null si aún no se ha generado).
     */
    Pregunta obtenerPreguntaActual();

    /**
     * Pasa a la siguiente pregunta y la devuelve.
     */
    Pregunta siguientePregunta();

    boolean responderPregunta(int indiceRespuesta);


    void finalizarPartida(boolean victoria);

    /**
     * Puntaje acumulado en la partida actual.
     */
    int getPuntajeActual();

    /**
     * Número de pregunta actual (1, 2, 3, ...).
     */
    int getNumeroPreguntaActual();


    EstadoJuego getEstadoJuego();

    Partida getPartidaActual();
}
