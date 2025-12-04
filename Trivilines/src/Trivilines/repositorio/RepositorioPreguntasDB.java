package Trivilines.repositorio;


import Trivilines.database.ConexionDB;
import Trivilines.database.DatabaseManager;
import Trivilines.modelo.Categoria;
import Trivilines.modelo.Pregunta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de RepositorioPreguntas usando SQLite.
 */
public class RepositorioPreguntasDB implements RepositorioPreguntas {

    @Override
    public List<Pregunta> listarTodas() {
        String sql = "SELECT p.id, p.texto, p.opcion1, p.opcion2, p.opcion3, p.opcion4, " +
                "p.indice_respuesta_correcta, p.categoria_id, p.nivel_dificultad, p.tiempo_limite_segundos, " +
                "c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.ruta_icono AS cat_icono " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id";

        List<Pregunta> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearPregunta(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todas las preguntas: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Pregunta buscarPorId(int id) {
        String sql = "SELECT p.id, p.texto, p.opcion1, p.opcion2, p.opcion3, p.opcion4, " +
                "p.indice_respuesta_correcta, p.categoria_id, p.nivel_dificultad, p.tiempo_limite_segundos, " +
                "c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.ruta_icono AS cat_icono " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.id = ?";

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPregunta(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pregunta por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pregunta> listarPorCategoria(int categoriaId) {
        String sql = "SELECT p.id, p.texto, p.opcion1, p.opcion2, p.opcion3, p.opcion4, " +
                "p.indice_respuesta_correcta, p.categoria_id, p.nivel_dificultad, p.tiempo_limite_segundos, " +
                "c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.ruta_icono AS cat_icono " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.categoria_id = ?";

        List<Pregunta> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, categoriaId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearPregunta(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar preguntas por categoría: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Pregunta> listarPorCategoriaYNivel(int categoriaId, int nivelDificultad) {
        String sql = "SELECT p.id, p.texto, p.opcion1, p.opcion2, p.opcion3, p.opcion4, " +
                "p.indice_respuesta_correcta, p.categoria_id, p.nivel_dificultad, p.tiempo_limite_segundos, " +
                "c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.ruta_icono AS cat_icono " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.categoria_id = ? AND p.nivel_dificultad = ?";

        List<Pregunta> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, categoriaId);
                ps.setInt(2, nivelDificultad);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearPregunta(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar preguntas por categoría y nivel: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Pregunta obtenerPreguntaAleatoria(int categoriaId, int nivelDificultad) {
        String sql = "SELECT p.id, p.texto, p.opcion1, p.opcion2, p.opcion3, p.opcion4, " +
                "p.indice_respuesta_correcta, p.categoria_id, p.nivel_dificultad, p.tiempo_limite_segundos, " +
                "c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.ruta_icono AS cat_icono " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.categoria_id = ? AND p.nivel_dificultad = ? " +
                "ORDER BY RANDOM() LIMIT 1";

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, categoriaId);
                ps.setInt(2, nivelDificultad);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPregunta(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pregunta aleatoria: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Pregunta guardar(Pregunta pregunta) {
        if (pregunta.getId() <= 0) {
            return insertar(pregunta);
        } else {
            actualizar(pregunta);
            return pregunta;
        }
    }

    private Pregunta insertar(Pregunta pregunta) {
        String sql = "INSERT INTO preguntas (" +
                "texto, opcion1, opcion2, opcion3, opcion4, " +
                "indice_respuesta_correcta, categoria_id, nivel_dificultad, tiempo_limite_segundos" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            int generado = DatabaseManager.ejecutarActualizacion(sql,
                    pregunta.getTexto(),
                    pregunta.getOpciones().get(0),
                    pregunta.getOpciones().get(1),
                    pregunta.getOpciones().get(2),
                    pregunta.getOpciones().get(3),
                    pregunta.getIndiceRespuestaCorrecta(),
                    pregunta.getCategoria() != null ? pregunta.getCategoria().getId() : 1,
                    pregunta.getNivelDificultad(),
                    pregunta.getTiempoLimiteSegundos()
            );
            if (generado > 0) {
                pregunta.setId(generado);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar pregunta: " + e.getMessage());
        }
        return pregunta;
    }

    private void actualizar(Pregunta pregunta) {
        String sql = "UPDATE preguntas SET " +
                "texto = ?, opcion1 = ?, opcion2 = ?, opcion3 = ?, opcion4 = ?, " +
                "indice_respuesta_correcta = ?, categoria_id = ?, nivel_dificultad = ?, tiempo_limite_segundos = ? " +
                "WHERE id = ?";

        try {
            DatabaseManager.ejecutarActualizacion(sql,
                    pregunta.getTexto(),
                    pregunta.getOpciones().get(0),
                    pregunta.getOpciones().get(1),
                    pregunta.getOpciones().get(2),
                    pregunta.getOpciones().get(3),
                    pregunta.getIndiceRespuestaCorrecta(),
                    pregunta.getCategoria() != null ? pregunta.getCategoria().getId() : 1,
                    pregunta.getNivelDificultad(),
                    pregunta.getTiempoLimiteSegundos(),
                    pregunta.getId()
            );
        } catch (SQLException e) {
            System.err.println("Error al actualizar pregunta: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM preguntas WHERE id = ?";
        try {
            DatabaseManager.ejecutarActualizacion(sql, id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar pregunta: " + e.getMessage());
        }
    }

    private Pregunta mapearPregunta(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String texto = rs.getString("texto");
        String op1 = rs.getString("opcion1");
        String op2 = rs.getString("opcion2");
        String op3 = rs.getString("opcion3");
        String op4 = rs.getString("opcion4");
        int idxCorrecta = rs.getInt("indice_respuesta_correcta");
        int categoriaId = rs.getInt("categoria_id");
        int nivel = rs.getInt("nivel_dificultad");
        int tiempo = rs.getInt("tiempo_limite_segundos");

        Categoria categoria = new Categoria(
                categoriaId,
                rs.getString("cat_nombre"),
                rs.getString("cat_desc"),
                rs.getString("cat_icono")
        );

        List<String> opciones = new ArrayList<>();
        opciones.add(op1);
        opciones.add(op2);
        opciones.add(op3);
        opciones.add(op4);

        return new Pregunta(id, texto, opciones, idxCorrecta, categoria, nivel, tiempo);
    }
}
