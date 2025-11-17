package Examen;


import java.sql.*;
import java.util.List;

import Examen.Modelo.Equipo;
import Examen.Modelo.Mantenimiento;
import Examen.Modelo.ParAsociado;

public class RepositorioBD {

    private String urlConexion;

    public RepositorioBD(String urlConexion) {
        this.urlConexion = urlConexion;   
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlConexion);
    }

    public void inicializarEsquema() throws SQLException {
        String sqlEquipo =
                "CREATE TABLE IF NOT EXISTS EQUIPO (" +
                        "codigo TEXT PRIMARY KEY, " +
                        "nombre TEXT, " +
                        "tipo TEXT, " +
                        "estado TEXT)";
        String sqlMantenimiento =
                "CREATE TABLE IF NOT EXISTS MANTENIMIENTO (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "equipo_codigo TEXT, " +
                        "fecha TEXT, " +
                        "descripcion TEXT, " +
                        "tecnico TEXT, " +
                        "costo REAL, " +
                        "FOREIGN KEY(equipo_codigo) REFERENCES EQUIPO(codigo))";

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            st.execute(sqlEquipo);
            st.execute(sqlMantenimiento);
        }
    }

    public void guardarTodo(List<ParAsociado<Equipo, Mantenimiento>> asociaciones) throws SQLException {
        inicializarEsquema();
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            String insertEquipo =
                    "INSERT OR IGNORE INTO EQUIPO(codigo, nombre, tipo, estado) VALUES (?,?,?,?)";
            String insertMant =
                    "INSERT INTO MANTENIMIENTO(equipo_codigo, fecha, descripcion, tecnico, costo) " +
                            "VALUES (?,?,?,?,?)";

            try (PreparedStatement psEq = con.prepareStatement(insertEquipo);
                 PreparedStatement psMt = con.prepareStatement(insertMant)) {

                for (ParAsociado<Equipo, Mantenimiento> par : asociaciones) {
                    Equipo e = par.getPrimero();
                    Mantenimiento m = par.getSegundo();

                    psEq.setString(1, e.getCodigo());
                    psEq.setString(2, e.getNombre());
                    psEq.setString(3, e.getTipo());
                    psEq.setString(4, e.getEstado());
                    psEq.addBatch();

                    psMt.setString(1, e.getCodigo());
                    psMt.setString(2, m.getFecha().toString());
                    psMt.setString(3, m.getDescripcion());
                    psMt.setString(4, m.getTecnico());
                    psMt.setDouble(5, m.getCosto());
                    psMt.addBatch();
                }
                psEq.executeBatch();
                psMt.executeBatch();
            }

            con.commit();
        }
    }
}
