package Pryct.notificaciones;

public class ObservadorEstadisticas implements ObservadorInventario {

    private int totalEquipos;
    private int totalMantenimientos;

    @Override
    public void manejarCambio(EventoInventario e) {
        switch (e.getTipo()) {
            case EQUIPO_REGISTRADO -> totalEquipos++;
            case MANTENIMIENTO_REGISTRADO -> totalMantenimientos++;
            default -> {}
        }
    }

    public int getTotalEquipos() { return totalEquipos; }
    public int getTotalMantenimientos() { return totalMantenimientos; }
}
