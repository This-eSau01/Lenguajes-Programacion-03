package hotel;

import java.time.LocalDate;

// Principio de Segregacion de Interfaces (ISP)
// Interfaz pequena y especifica para la gestion de reservas.
public interface GestionReservas {
    Reserva realizarReserva(Cliente cliente, String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin);
    boolean cancelarReserva(Reserva reserva);
}