package Trivilines.repositorio;
import Trivilines.modelo.Pregunta;

import java.util.List;

public interface RepositorioPreguntas {

    List<Pregunta> listarTodas();
    Pregunta buscarPorId(int id);
    List<Pregunta> listarPorCategoria(int categoriaId);
    List<Pregunta> listarPorCategoriaYNivel(int categoriaId, int nivelDificultad);

    Pregunta obtenerPreguntaAleatoria(int categoriaId, int nivelDificultad);

    Pregunta guardar(Pregunta pregunta);

    void eliminar(int id);
}
