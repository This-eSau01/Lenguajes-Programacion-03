package hotel;

import java.time.LocalDate;

public interface GestionReservas {
    Reserva realizarReserva(Cliente cliente, String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin);
    boolean cancelarReserva(Reserva reserva);
}
