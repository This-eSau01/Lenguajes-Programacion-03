package Trivilines.repositorio;
import Trivilines.modelo.Partida;

import java.util.List;

public interface RepositorioPartidas {

    List<Partida> listarTodas();
    List<Partida> listarPorJugador(int jugadorId);
    Partida buscarPorId(int id);
    /**
     * Inserta o actualiza según si tiene id > 0.
     */
    Partida guardar(Partida partida);

    void eliminar(int id);

    /**
     * Cuenta cuántas partidas ganadas tiene un jugador.
     */
    int contarPartidasGanadasPorJugador(int jugadorId);
}
