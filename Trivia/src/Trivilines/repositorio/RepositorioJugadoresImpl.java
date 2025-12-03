package Trivilines.repositorio;

import Trivilines.database.ConexionDB;
import Trivilines.database.DatabaseManager;
import Trivilines.modelo.Jugador;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de RepositorioJugadores usando SQLite.
 */
public class RepositorioJugadoresImpl implements RepositorioJugadores {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public List<Jugador> listarTodos() {
        String sql = "SELECT * FROM jugadores ORDER BY nombre ASC";
        List<Jugador> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearJugador(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar jugadores: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Jugador buscarPorId(int id) {
        String sql = "SELECT * FROM jugadores WHERE id = ?";
        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearJugador(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar jugador por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Jugador buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM jugadores WHERE nombre = ?";
        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearJugador(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar jugador por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Jugador guardar(Jugador jugador) {
        if (jugador.getId() <= 0) {
            return insertar(jugador);
        } else {
            actualizar(jugador);
            return jugador;
        }
    }

    private Jugador insertar(Jugador jugador) {
        String sql = "INSERT INTO jugadores (" +
                "nombre, fecha_registro, partidas_jugadas, partidas_ganadas, " +
                "puntaje_maximo, puntaje_total_acumulado" +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        String fecha = jugador.getFechaRegistro() != null
                ? jugador.getFechaRegistro().format(FORMATTER)
                : LocalDateTime.now().format(FORMATTER);

        try {
            int generado = DatabaseManager.ejecutarActualizacion(sql,
                    jugador.getNombre(),
                    fecha,
                    jugador.getPartidasJugadas(),
                    jugador.getPartidasGanadas(),
                    jugador.getPuntajeMaximo(),
                    jugador.getPuntajeTotalAcumulado()
            );
            if (generado > 0) {
                jugador.setId(generado);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar jugador: " + e.getMessage());
        }
        return jugador;
    }

    private void actualizar(Jugador jugador) {
        String sql = "UPDATE jugadores SET " +
                "nombre = ?, fecha_registro = ?, partidas_jugadas = ?, partidas_ganadas = ?, " +
                "puntaje_maximo = ?, puntaje_total_acumulado = ? " +
                "WHERE id = ?";

        String fecha = jugador.getFechaRegistro() != null
                ? jugador.getFechaRegistro().format(FORMATTER)
                : LocalDateTime.now().format(FORMATTER);

        try {
            DatabaseManager.ejecutarActualizacion(sql,
                    jugador.getNombre(),
                    fecha,
                    jugador.getPartidasJugadas(),
                    jugador.getPartidasGanadas(),
                    jugador.getPuntajeMaximo(),
                    jugador.getPuntajeTotalAcumulado(),
                    jugador.getId()
            );
        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM jugadores WHERE id = ?";
        try {
            DatabaseManager.ejecutarActualizacion(sql, id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar jugador: " + e.getMessage());
        }
    }

    @Override
    public List<Jugador> listarTopPorPuntajeMaximo(int limite) {
        String sql = "SELECT * FROM jugadores " +
                "ORDER BY puntaje_maximo DESC, nombre ASC " +
                "LIMIT ?";
        List<Jugador> lista = new ArrayList<>();

        try {
            Connection conn = ConexionDB.obtenerConexion();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, limite);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearJugador(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar top jugadores por puntaje máximo: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Jugador> listarTopPorPuntajePromedio(int limite) {
        // Aquí calculamos el promedio en memoria a partir de los campos
        List<Jugador> todos = listarTodos();
        todos.sort((a, b) -> Double.compare(b.getPuntajePromedio(), a.getPuntajePromedio()));
        if (todos.size() > limite) {
            return new ArrayList<>(todos.subList(0, limite));
        }
        return todos;
    }

    private Jugador mapearJugador(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String fechaStr = rs.getString("fecha_registro");
        int jugadas = rs.getInt("partidas_jugadas");
        int ganadas = rs.getInt("partidas_ganadas");
        int maximo = rs.getInt("puntaje_maximo");
        int acumulado = rs.getInt("puntaje_total_acumulado");

        LocalDateTime fecha = null;
        if (fechaStr != null && !fechaStr.isEmpty()) {
            try {
                fecha = LocalDateTime.parse(fechaStr, FORMATTER);
            } catch (Exception e) {
                fecha = LocalDateTime.now();
            }
        }

        return new Jugador(id, nombre, fecha, jugadas, ganadas, maximo, acumulado);
    }
}
