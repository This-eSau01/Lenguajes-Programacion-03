package Trivilines.repositorio;


import Trivilines.modelo.Jugador;

import java.util.List;

public interface RepositorioJugadores {

    List<Jugador> listarTodos();

    Jugador buscarPorId(int id);

    Jugador buscarPorNombre(String nombre);

    /**
     * Inserta o actualiza según si tiene id > 0.
     */
    Jugador guardar(Jugador jugador);

    void eliminar(int id);

    List<Jugador> listarTopPorPuntajeMaximo(int limite);

    List<Jugador> listarTopPorPuntajePromedio(int limite);
}
