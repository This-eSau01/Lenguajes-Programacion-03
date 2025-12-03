package Trivilines.repositorio;

import Trivilines.modelo.Categoria;
import Trivilines.modelo.Pregunta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Implementación en memoria para pruebas. No usa base de datos.
 */
public class RepositorioPreguntasImpl implements RepositorioPreguntas {

    private final List<Pregunta> preguntas = new ArrayList<>();
    private final AtomicInteger generadorId = new AtomicInteger(0);
    private final Random random = new Random();

    public RepositorioPreguntasImpl() {
        // Puedes cargar preguntas de prueba aquí si quieres.
    }

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public Pregunta buscarPorId(int id) {
        return preguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Pregunta> listarPorCategoria(int categoriaId) {
        return preguntas.stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId() == categoriaId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pregunta> listarPorCategoriaYNivel(int categoriaId, int nivelDificultad) {
        return preguntas.stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId() == categoriaId)
                .filter(p -> p.getNivelDificultad() == nivelDificultad)
                .collect(Collectors.toList());
    }

    @Override
    public Pregunta obtenerPreguntaAleatoria(int categoriaId, int nivelDificultad) {
        List<Pregunta> filtradas = listarPorCategoriaYNivel(categoriaId, nivelDificultad);
        if (filtradas.isEmpty()) {
            return null;
        }
        return filtradas.get(random.nextInt(filtradas.size()));
    }

    @Override
    public Pregunta guardar(Pregunta pregunta) {
        if (pregunta.getId() <= 0) {
            // Nuevo
            int nuevoId = generadorId.incrementAndGet();
            pregunta.setId(nuevoId);
            preguntas.add(pregunta);
        } else {
            // Actualizar
            eliminar(pregunta.getId());
            preguntas.add(pregunta);
        }
        return pregunta;
    }

    @Override
    public void eliminar(int id) {
        preguntas.removeIf(p -> p.getId() == id);
    }

    /**
     * Método auxiliar para agregar rápidamente una pregunta.
     */
    public Pregunta agregarPregunta(String texto,
                                    List<String> opciones,
                                    int indiceCorrecta,
                                    Categoria categoria,
                                    int nivel,
                                    int tiempoLimite) {

        Pregunta p = new Pregunta(0, texto, opciones, indiceCorrecta, categoria, nivel, tiempoLimite);
        return guardar(p);
    }
}
