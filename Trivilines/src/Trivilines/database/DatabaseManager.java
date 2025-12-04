package Trivilines.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;


public final class DatabaseManager {

    private static final String RUTA_SCHEMA = "database/scripts/schema.sql";

    private DatabaseManager() {
        // Evita instanciación
    }


    public static void inicializarBaseDeDatos() {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            if (conn == null) {
                System.err.println("No se pudo obtener conexión a la base de datos.");
                return;
            }
            ejecutarScript(conn, RUTA_SCHEMA);
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }


    private static void ejecutarScript(Connection conn, String rutaClasspath) {
        try (InputStream in = DatabaseManager.class.getClassLoader()
                .getResourceAsStream(rutaClasspath)) {

            if (in == null) {
                System.err.println("No se encontró el script SQL: " + rutaClasspath);
                return;
            }

            String sqlCompleto = new String(in.readAllBytes(), StandardCharsets.UTF_8);

            String[] sentencias = sqlCompleto.split(";");

            for (String sentencia : sentencias) {
                String sql = sentencia.trim();
                if (sql.isEmpty()) {
                    continue;
                }
                try (Statement st = conn.createStatement()) {
                    st.execute(sql);
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el script SQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error ejecutando el script SQL: " + e.getMessage());
        }
    }

    public static int ejecutarActualizacion(String sql, Object... params) throws SQLException {
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepararParametros(ps, params);

            int filas = ps.executeUpdate();

            // Intenta devolver clave generada
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return filas;
        }
    }


    public static ResultSet ejecutarConsulta(String sql, Object... params) throws SQLException {
        Connection conn = ConexionDB.obtenerConexion();
        PreparedStatement ps = conn.prepareStatement(sql);
        prepararParametros(ps, params);
        // No cerramos conn ni ps aquí; se cierran desde el código que consuma este ResultSet
        return ps.executeQuery();
    }

    private static void prepararParametros(PreparedStatement ps, Object... params) throws SQLException {
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }


    public static void cerrarSilencioso(AutoCloseable recurso) {
        if (recurso != null) {
            try {
                recurso.close();
            } catch (Exception ignored) {
                // Ignorado a propósito
            }
        }
    }
}
