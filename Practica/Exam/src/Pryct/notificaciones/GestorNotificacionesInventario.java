package Pryct.notificaciones;

import java.util.ArrayList;
import java.util.List;

public class GestorNotificacionesInventario {

    private final List<ObservadorInventario> observadores = new ArrayList<>();

    public void suscribir(ObservadorInventario o) {
        if (!observadores.contains(o)) observadores.add(o);
    }

    public void desuscribir(ObservadorInventario o) {
        observadores.remove(o);
    }

    public void notificar(EventoInventario e) {
        for (ObservadorInventario o : observadores) {
            o.manejarCambio(e);
        }
    }
}
