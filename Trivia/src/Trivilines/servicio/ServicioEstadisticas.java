package Trivilines.servicio;

import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;

import java.util.List;

public interface ServicioEstadisticas {

    /**
     * Ranking de jugadores por puntaje máximo.
     */
    List<Jugador> obtenerRankingPuntajeMaximo(int limite);

    /**
     * Ranking de jugadores por puntaje promedio.
     */
    List<Jugador> obtenerRankingPuntajePromedio(int limite);

    /**
     * Lista de partidas de un jugador.
     */
    List<Partida> obtenerHistorialPartidasJugador(int jugadorId);

    /**
     * Porcentaje de victorias de un jugador (0 a 100).
     */
    double obtenerPorcentajeVictoriasJugador(int jugadorId);

    /**
     * Total de partidas jugadas en el sistema.
     */
    int obtenerTotalPartidas();
}
