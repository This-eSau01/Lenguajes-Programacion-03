package Trivilines.servicio;

import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;
import Trivilines.repositorio.RepositorioJugadores;
import Trivilines.repositorio.RepositorioPartidas;

import java.util.List;

public class ServicioEstadisticasImpl implements ServicioEstadisticas {

    private final RepositorioJugadores repositorioJugadores;
    private final RepositorioPartidas repositorioPartidas;

    public ServicioEstadisticasImpl(RepositorioJugadores repositorioJugadores,
                                    RepositorioPartidas repositorioPartidas) {
        this.repositorioJugadores = repositorioJugadores;
        this.repositorioPartidas = repositorioPartidas;
    }

    @Override
    public List<Jugador> obtenerRankingPuntajeMaximo(int limite) {
        return repositorioJugadores.listarTopPorPuntajeMaximo(limite);
    }

    @Override
    public List<Jugador> obtenerRankingPuntajePromedio(int limite) {
        return repositorioJugadores.listarTopPorPuntajePromedio(limite);
    }

    @Override
    public List<Partida> obtenerHistorialPartidasJugador(int jugadorId) {
        return repositorioPartidas.listarPorJugador(jugadorId);
    }

    @Override
    public double obtenerPorcentajeVictoriasJugador(int jugadorId) {
        List<Partida> partidas = repositorioPartidas.listarPorJugador(jugadorId);
        if (partidas.isEmpty()) {
            return 0.0;
        }
        long ganadas = partidas.stream().filter(Partida::isVictoria).count();
        return ganadas * 100.0 / partidas.size();
    }

    @Override
    public int obtenerTotalPartidas() {
        return repositorioPartidas.listarTodas().size();
    }
}
