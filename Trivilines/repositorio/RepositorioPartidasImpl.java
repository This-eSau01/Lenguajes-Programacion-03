package Trivilines.repositorio;

import Trivilines.database.ConexionDB;
import Trivilines.database.DatabaseManager;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de RepositorioPartidas usando SQLite.
 * Nota: solo se persisten los campos básicos de Partida (no el mapa por categoría).
 */
public class RepositorioPartidasImpl implements RepositorioPartidas {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final RepositorioJugadores repositorioJugadores;

    public RepositorioPartidasImpl(RepositorioJugadores repositorioJugadores) {
        this.repositorioJugadores = repositorioJugadores;
    }

    @Override
    public List<Partida> listarTodas() {
        String sql = "SELECT * FROM partidas ORDER BY fecha_inicio DESC";
        List<Partida> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearPartida(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar partidas: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Partida> listarPorJugador(int jugadorId) {
        String sql = "SELECT * FROM partidas WHERE jugador_id = ? ORDER BY fecha_inicio DESC";
        List<Partida> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, jugadorId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearPartida(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar partidas por jugador: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Partida buscarPorId(int id) {
        String sql = "SELECT * FROM partidas WHERE id = ?";
        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearPartida(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar partida por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Partida guardar(Partida partida) {
        if (partida.getId() <= 0) {
            return insertar(partida);
        } else {
            actualizar(partida);
            return partida;
        }
    }

    private Partida insertar(Partida partida) {
        String sql = "INSERT INTO partidas (" +
                "jugador_id, fecha_inicio, fecha_fin, preguntas_respondidas, " +
                "respuestas_correctas, respuestas_incorrectas, puntaje_total, victoria" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String inicio = partida.getFechaHoraInicio() != null
                ? partida.getFechaHoraInicio().format(FORMATTER)
                : LocalDateTime.now().format(FORMATTER);

        String fin = partida.getFechaHoraFin() != null
                ? partida.getFechaHoraFin().format(FORMATTER)
                : inicio;

        int victoriaInt = partida.isVictoria() ? 1 : 0;

        try {
            int generado = DatabaseManager.ejecutarActualizacion(sql,
                    partida.getJugador() != null ? partida.getJugador().getId() : null,
                    inicio,
                    fin,
                    partida.getPreguntasRespondidas(),
                    partida.getRespuestasCorrectas(),
                    partida.getRespuestasIncorrectas(),
                    partida.getPuntajeTotal(),
                    victoriaInt
            );
            if (generado > 0) {
                partida.setId(generado);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar partida: " + e.getMessage());
        }
        return partida;
    }

    private void actualizar(Partida partida) {
        String sql = "UPDATE partidas SET " +
                "jugador_id = ?, fecha_inicio = ?, fecha_fin = ?, preguntas_respondidas = ?, " +
                "respuestas_correctas = ?, respuestas_incorrectas = ?, puntaje_total = ?, victoria = ? " +
                "WHERE id = ?";

        String inicio = partida.getFechaHoraInicio() != null
                ? partida.getFechaHoraInicio().format(FORMATTER)
                : LocalDateTime.now().format(FORMATTER);

        String fin = partida.getFechaHoraFin() != null
                ? partida.getFechaHoraFin().format(FORMATTER)
                : inicio;

        int victoriaInt = partida.isVictoria() ? 1 : 0;

        try {
            DatabaseManager.ejecutarActualizacion(sql,
                    partida.getJugador() != null ? partida.getJugador().getId() : null,
                    inicio,
                    fin,
                    partida.getPreguntasRespondidas(),
                    partida.getRespuestasCorrectas(),
                    partida.getRespuestasIncorrectas(),
                    partida.getPuntajeTotal(),
                    victoriaInt,
                    partida.getId()
            );
        } catch (SQLException e) {
            System.err.println("Error al actualizar partida: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM partidas WHERE id = ?";
        try {
            DatabaseManager.ejecutarActualizacion(sql, id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar partida: " + e.getMessage());
        }
    }

    @Override
    public int contarPartidasGanadasPorJugador(int jugadorId) {
        String sql = "SELECT COUNT(*) AS total FROM partidas WHERE jugador_id = ? AND victoria = 1";
        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, jugadorId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar partidas ganadas: " + e.getMessage());
        }
        return 0;
    }

    private Partida mapearPartida(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int jugadorId = rs.getInt("jugador_id");
        String inicioStr = rs.getString("fecha_inicio");
        String finStr = rs.getString("fecha_fin");
        int respondidas = rs.getInt("preguntas_respondidas");
        int correctas = rs.getInt("respuestas_correctas");
        int incorrectas = rs.getInt("respuestas_incorrectas");
        int puntaje = rs.getInt("puntaje_total");
        boolean victoria = rs.getInt("victoria") == 1;

        LocalDateTime inicio = null;
        LocalDateTime fin = null;

        if (inicioStr != null && !inicioStr.isEmpty()) {
            try {
                inicio = LocalDateTime.parse(inicioStr, FORMATTER);
            } catch (Exception e) {
                inicio = LocalDateTime.now();
            }
        }

        if (finStr != null && !finStr.isEmpty()) {
            try {
                fin = LocalDateTime.parse(finStr, FORMATTER);
            } catch (Exception e) {
                fin = inicio != null ? inicio : LocalDateTime.now();
            }
        }

        Jugador jugador = repositorioJugadores.buscarPorId(jugadorId);

        Partida partida = new Partida();
        partida.setId(id);
        partida.setJugador(jugador);
        partida.setFechaHoraInicio(inicio);
        partida.setFechaHoraFin(fin);
        partida.setPreguntasRespondidas(respondidas);
        partida.setRespuestasCorrectas(correctas);
        partida.setRespuestasIncorrectas(incorrectas);
        partida.setPuntajeTotal(puntaje);
        partida.setVictoria(victoria);

        return partida;
    }
}
