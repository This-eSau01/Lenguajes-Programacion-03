package Pryct.controlador;

import Pryct.estrategias.EstrategiaOrdenInventario;
import Pryct.estrategias.EstrategiaPresentacionEquipo;
import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;
import Pryct.modelo.ParAsociado;
import Pryct.notificaciones.*;
import Pryct.repositorio.RepositorioArchivo;
import Pryct.repositorio.RepositorioAsociativo;
import Pryct.repositorio.RepositorioBD;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorInventario {

    private final List<Equipo> equipos = new ArrayList<>();
    private final RepositorioAsociativo<Equipo, Mantenimiento> asociaciones = new RepositorioAsociativo<>();

    private final RepositorioArchivo repoArchivo;
    private final RepositorioBD repoBD;

    private final GestorNotificacionesInventario notificador;
    private final ObservadorLogArchivo obsLog;
    private final ObservadorEstadisticas obsEst;

    private boolean registroEventosActivo = false;

    public ControladorInventario(GestorNotificacionesInventario notificador) {
        this.notificador = notificador;
        this.repoArchivo = new RepositorioArchivo("inventario.txt");
        this.repoBD = new RepositorioBD("jdbc:sqlite:inventario.db");
        this.obsLog = new ObservadorLogArchivo("inventario.log");
        this.obsEst = new ObservadorEstadisticas();
        notificador.suscribir(obsEst);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void registrarEquipo(String codigo, String nombre, String tipo,
                                String estado, double valor, String descTec) {

        Equipo e = new Equipo(codigo, nombre, tipo, estado, valor, descTec);
        equipos.add(e);

        notificador.notificar(new EventoInventario(
                TipoEventoInventario.EQUIPO_REGISTRADO,
                e, null, "ControladorInventario", "Registro de equipo"
        ));
    }

    public void registrarMantenimiento(String codEquipo, LocalDate fecha,
                                       String descripcion, String tecnico, double costo) {

        Equipo e = buscarEquipo(codEquipo);
        if (e == null) return;

        Mantenimiento m = new Mantenimiento(fecha, descripcion, tecnico, costo);
        e.getMantenimientos().add(m);
        asociaciones.agregar(e, m);

        notificador.notificar(new EventoInventario(
                TipoEventoInventario.MANTENIMIENTO_REGISTRADO,
                e, m, "ControladorInventario", "Registro de mantenimiento"
        ));
    }

    private Equipo buscarEquipo(String codigo) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) return e;
        }
        return null;
    }

    public String generarReporte(EstrategiaOrdenInventario ord,
                                 EstrategiaPresentacionEquipo vista) {

        List<ParAsociado<Equipo, Mantenimiento>> lista = asociaciones.listar();
        ord.ordenar(lista);

        StringBuilder sb = new StringBuilder();
        for (ParAsociado<Equipo, Mantenimiento> par : lista) {
            sb.append(vista.formatear(par.getPrimero())).append("\n");
            if (par.getSegundo() != null) {
                sb.append("  ").append(par.getSegundo().toString()).append("\n");
            }
            sb.append("------------------------------------------------------\n");
        }
        return sb.toString();
    }

    public void guardarEnArchivo() throws IOException {
        repoArchivo.guardar(equipos);
    }

    public void cargarDesdeArchivo() throws IOException {
        equipos.clear();
        asociaciones.limpiar();
        equipos.addAll(repoArchivo.cargar());

        for (Equipo e : equipos) {
            for (Mantenimiento m : e.getMantenimientos()) {
                asociaciones.agregar(e, m);
            }
        }

        notificador.notificar(new EventoInventario(
                TipoEventoInventario.INVENTARIO_CARGADO,
                null, null, "ControladorInventario", "Cargado desde archivo"
        ));
    }

    public void guardarEnBD() throws SQLException {
        repoBD.guardar(equipos);
    }

    public void cargarDesdeBD() throws SQLException {
        equipos.clear();
        asociaciones.limpiar();
        equipos.addAll(repoBD.cargar());
        for (Equipo e : equipos) {
            for (Mantenimiento m : e.getMantenimientos()) {
                asociaciones.agregar(e, m);
            }
        }
        notificador.notificar(new EventoInventario(
                TipoEventoInventario.INVENTARIO_CARGADO,
                null, null, "ControladorInventario", "Cargado desde BD"
        ));
    }

    public void limpiarInventario() {
        equipos.clear();
        asociaciones.limpiar();
        notificador.notificar(new EventoInventario(
                TipoEventoInventario.INVENTARIO_LIMPIADO,
                null, null, "ControladorInventario", "Inventario limpiado"
        ));
    }

    public void activarRegistroEventos(boolean activo) {
        if (activo && !registroEventosActivo) {
            notificador.suscribir(obsLog);
            registroEventosActivo = true;
        } else if (!activo && registroEventosActivo) {
            notificador.desuscribir(obsLog);
            registroEventosActivo = false;
        }
    }
}
