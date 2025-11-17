package Examen;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Examen.Modelo.Equipo;
import Examen.Modelo.Mantenimiento;
import Examen.Modelo.ParAsociado;

public class ControladorInventario {

    private List<Equipo> equipos = new ArrayList<>();
    private RepositorioAsociativo<Equipo, Mantenimiento> repoAsociativo =
            new RepositorioAsociativo<>();
    private RepositorioArchivo repoArchivo;
    private RepositorioBD repoBD;

    public ControladorInventario() {
        this.repoArchivo = new RepositorioArchivo("inventario.dat");
        this.repoBD = new RepositorioBD("jdbc:sqlite:inventario.db");
    }

    public void registrarEquipo(String codigo, String nombre, String tipo, String estado) {
        Equipo e = new Equipo(codigo, nombre, tipo, estado);
        equipos.add(e);
    }

    public boolean registrarMantenimiento(String codigoEquipo,
                                          LocalDate fecha,
                                          String descripcion,
                                          String tecnico,
                                          double costo) {
        Equipo equipo = buscarEquipoPorCodigo(codigoEquipo);
        if (equipo == null) {
            return false;
        }
        Mantenimiento m = new Mantenimiento(fecha, descripcion, tecnico, costo);
        repoAsociativo.agregar(equipo, m);
        return true;
    }

    public List<ParAsociado<Equipo, Mantenimiento>> listarAsociaciones() {
        return repoAsociativo.listar();
    }

    public void guardarEnArchivo() throws IOException {
        repoArchivo.guardar(repoAsociativo.listar());
    }

    public void cargarDesdeArchivo() throws IOException, ClassNotFoundException {
        List<ParAsociado<Equipo, Mantenimiento>> cargadas = repoArchivo.cargar();
        repoAsociativo.listar();
        repoAsociativo.agregarTodo(cargadas);
        equipos.clear();
        for (ParAsociado<Equipo, Mantenimiento> p : cargadas) {
            if (!equipos.contains(p.getPrimero())) {
                equipos.add(p.getPrimero());
            }
        }
    }
    public void guardarEnBaseDeDatos() throws SQLException {
        repoBD.guardarTodo(repoAsociativo.listar());
    }

    private Equipo buscarEquipoPorCodigo(String codigo) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    public List<Equipo> listarEquipos() {
        return new ArrayList<>(equipos);
    }
}
