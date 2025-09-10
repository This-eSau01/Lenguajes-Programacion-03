package hotel;

import java.time.LocalDate;


public interface GestionHabitaciones {
    Habitacion consultarDisponibilidad(String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin);
    void asignarHabitacion(Reserva reserva, Habitacion habitacion);
    void liberarHabitacion(Habitacion habitacion);
}