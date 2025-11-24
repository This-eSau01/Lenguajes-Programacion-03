package Pryct.repositorio;

import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioBD {

    private final String url;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver SQLite JDBC cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("No se pudo cargar el driver SQLite JDBC.");
            e.printStackTrace();
        }
    }

    public RepositorioBD(String url) {
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void guardar(List<Equipo> equipos) throws SQLException {
        inicializarEsquema();
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            String sqlEq = "INSERT OR REPLACE INTO EQUIPO(codigo,nombre,tipo,estado,valor,descripcionTecnica) " +
                    "VALUES(?,?,?,?,?,?)";
            String sqlDelMant = "DELETE FROM MANTENIMIENTO WHERE equipo_codigo = ?";
            String sqlMant = "INSERT INTO MANTENIMIENTO(equipo_codigo,fecha,descripcion,tecnico,costo) " +
                    "VALUES(?,?,?,?,?)";

            try (PreparedStatement psEq = con.prepareStatement(sqlEq);
                 PreparedStatement psDel = con.prepareStatement(sqlDelMant);
                 PreparedStatement psMant = con.prepareStatement(sqlMant)) {

                for (Equipo e : equipos) {
                    psEq.setString(1, e.getCodigo());
                    psEq.setString(2, e.getNombre());
                    psEq.setString(3, e.getTipo());
                    psEq.setString(4, e.getEstado());
                    psEq.setDouble(5, e.getValor());
                    psEq.setString(6, e.getDescripcionTecnica());
                    psEq.executeUpdate();

                    psDel.setString(1, e.getCodigo());
                    psDel.executeUpdate();

                    for (Mantenimiento m : e.getMantenimientos()) {
                        psMant.setString(1, e.getCodigo());
                        psMant.setString(2, m.getFecha().toString());
                        psMant.setString(3, m.getDescripcion());
                        psMant.setString(4, m.getTecnico());
                        psMant.setDouble(5, m.getCosto());
                        psMant.executeUpdate();
                    }
                }
            }
            con.commit();
        }
    }

    public List<Equipo> cargar() throws SQLException {
        inicializarEsquema();
        List<Equipo> equipos = new ArrayList<>();
        try (Connection con = getConnection()) {
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM EQUIPO")) {
                while (rs.next()) {
                    Equipo e = new Equipo(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("tipo"),
                            rs.getString("estado"),
                            rs.getDouble("valor"),
                            rs.getString("descripcionTecnica")
                    );
                    equipos.add(e);
                }
            }

            String sqlMant = "SELECT * FROM MANTENIMIENTO WHERE equipo_codigo = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlMant)) {
                for (Equipo e : equipos) {
                    ps.setString(1, e.getCodigo());
                    try (ResultSet rsM = ps.executeQuery()) {
                        while (rsM.next()) {
                            Mantenimiento m = new Mantenimiento(
                                    java.time.LocalDate.parse(rsM.getString("fecha")),
                                    rsM.getString("descripcion"),
                                    rsM.getString("tecnico"),
                                    rsM.getDouble("costo")
                            );
                            e.getMantenimientos().add(m);
                        }
                    }
                }
            }
        }
        return equipos;
    }

    private void inicializarEsquema() throws SQLException {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.execute("""
                    CREATE TABLE IF NOT EXISTS EQUIPO(
                        codigo TEXT PRIMARY KEY,
                        nombre TEXT,
                        tipo TEXT,
                        estado TEXT,
                        valor REAL,
                        descripcionTecnica TEXT
                    )
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS MANTENIMIENTO(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        equipo_codigo TEXT,
                        fecha TEXT,
                        descripcion TEXT,
                        tecnico TEXT,
                        costo REAL,
                        FOREIGN KEY(equipo_codigo) REFERENCES EQUIPO(codigo)
                    )
                    """);
        }
    }
}
