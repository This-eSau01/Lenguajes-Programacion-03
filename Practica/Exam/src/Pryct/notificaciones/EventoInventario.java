package Pryct.notificaciones;

import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;

public class EventoInventario {

    private final TipoEventoInventario tipo;
    private final Equipo equipo;
    private final Mantenimiento mantenimiento;
    private final String origen;
    private final String detalle;

    public EventoInventario(TipoEventoInventario tipo,
                            Equipo equipo,
                            Mantenimiento mantenimiento,
                            String origen,
                            String detalle) {
        this.tipo = tipo;
        this.equipo = equipo;
        this.mantenimiento = mantenimiento;
        this.origen = origen;
        this.detalle = detalle;
    }

    public TipoEventoInventario getTipo() { return tipo; }
    public Equipo getEquipo() { return equipo; }
    public Mantenimiento getMantenimiento() { return mantenimiento; }
    public String getOrigen() { return origen; }
    public String getDetalle() { return detalle; }
}
