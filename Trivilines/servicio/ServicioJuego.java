package Trivilines.servicio;

import Trivilines.modelo.EstadoJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;
import Trivilines.modelo.Pregunta;

public interface ServicioJuego {

    /**
     * Inicia una nueva partida para el jugador dado.
     *
     * @param jugador     jugador que va a jugar.
     * @param categoriaId categoría seleccionada (id en BD).
     * @param nivel       nivel de dificultad (1 = fácil, 2 = medio, 3 = difícil).
     */
    void iniciarNuevaPartida(Jugador jugador, int categoriaId, int nivel);

    /**
     * Devuelve la pregunta actual (puede ser null si aún no se ha generado).
     */
    Pregunta obtenerPreguntaActual();

    /**
     * Pasa a la siguiente pregunta y la devuelve.
     */
    Pregunta siguientePregunta();

    /**
     * Registra la respuesta del jugador a la pregunta actual.
     *
     * @param indiceRespuesta índice de la opción elegida por el jugador.
     * @return true si la respuesta es correcta, false si es incorrecta.
     */
    boolean responderPregunta(int indiceRespuesta);

    /**
     * Finaliza la partida actual (por ejemplo al llegar al límite de preguntas
     * o porque el jugador abandona).
     *
     * @param victoria true si se considera que ganó, false en caso contrario.
     */
    void finalizarPartida(boolean victoria);

    /**
     * Puntaje acumulado en la partida actual.
     */
    int getPuntajeActual();

    /**
     * Número de pregunta actual (1, 2, 3, ...).
     */
    int getNumeroPreguntaActual();

    /**
     * Devuelve el estado actual del juego (EN_JUEGO, FINALIZADO, etc.).
     */
    EstadoJuego getEstadoJuego();

    /**
     * Devuelve la partida actual (puede ser null si no hay partida en curso).
     */
    Partida getPartidaActual();
}
