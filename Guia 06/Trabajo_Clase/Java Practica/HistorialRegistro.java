package default1;

import java.time.LocalDateTime;

public class HistorialRegistro {
    private final String nombrePlato;
    private final TipoPlato tipo;
    private final EstadoPedido estadoFinal;
    private final LocalDateTime fechaHora;
    private final String accion;

    public HistorialRegistro(String nombrePlato, TipoPlato tipo, EstadoPedido estadoFinal, String accion) {
        this.nombrePlato = nombrePlato;
        this.tipo = tipo;
        this.estadoFinal = estadoFinal;
        this.fechaHora = LocalDateTime.now();
        this.accion = accion;
    }

    @Override
    public String toString() {
        return fechaHora + " | " + accion + " | " + nombrePlato + " (" + tipo + ") -> " + estadoFinal;
    }
}