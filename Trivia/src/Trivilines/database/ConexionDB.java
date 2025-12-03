package Trivilines.database;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión única (singleton) a la base de datos SQLite.
 */
public final class ConexionDB {

    private static final String NOMBRE_BD = "trivia.db";
    private static final String RUTA_CLASSPATH = "resources/database/" + NOMBRE_BD;

    private static Connection conexion;

    static {
        // Carga del driver SQLite (org.xerial)
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("No se pudo cargar el driver SQLite: " + e.getMessage());
        }
    }

    private ConexionDB() {
        // Evita instanciación
    }

    /**
     * Devuelve una conexión activa a la base de datos. Mantiene una sola
     * conexión compartida para toda la aplicación.
     */
    public static synchronized Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = crearConexion();
        }
        return conexion;
    }

    private static Connection crearConexion() throws SQLException {
        String urlConexion;

        try {
            // Intenta localizar el archivo trivia.db dentro de resources/database
            URL recurso = ConexionDB.class.getClassLoader().getResource(RUTA_CLASSPATH);
            if (recurso != null) {
                Path path = Paths.get(recurso.toURI());
                urlConexion = "jdbc:sqlite:" + path.toString();
            } else {
                // Fallback: crea/usa trivia.db en el directorio de trabajo
                System.err.println("No se encontró " + RUTA_CLASSPATH + " en el classpath. " +
                        "Se usará un archivo local " + NOMBRE_BD + " en el directorio actual.");
                urlConexion = "jdbc:sqlite:" + NOMBRE_BD;
            }
        } catch (Exception e) {
            // Cualquier problema con la ruta usa un archivo local
            System.err.println("Error al localizar la base de datos en resources. " +
                    "Se usará un archivo local: " + e.getMessage());
            urlConexion = "jdbc:sqlite:" + NOMBRE_BD;
        }

        return DriverManager.getConnection(urlConexion);
    }

    /**
     * Cierra la conexión global si está abierta.
     */
    public static synchronized void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                conexion = null;
            }
        }
    }
}
