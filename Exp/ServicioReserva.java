package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioReserva implements GestionReservas {
    private List<Reserva> reservas = new ArrayList<>();
    private GestionHabitaciones servicioHabitaciones;

    public ServicioReserva(GestionHabitaciones servicioHabitaciones) {
        this.servicioHabitaciones = servicioHabitaciones;
    }

    @Override
    public Reserva realizarReserva(Cliente cliente, String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        if (cliente == null) {
            throw new ReservaException("El cliente no puede ser nulo.");
        }
        if (tipoHabitacion == null || tipoHabitacion.isEmpty()) {
            throw new ReservaException("El tipo de habitacion no puede ser nulo o vacio.");
        }
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            throw new ReservaException("Las fechas de reserva no son validas.");
        }

        Habitacion habitacionDisponible = servicioHabitaciones.consultarDisponibilidad(tipoHabitacion, fechaInicio, fechaFin);
        if (habitacionDisponible == null) {
            throw new HabitacionNoEncontrada("No hay habitaciones disponibles del tipo " + tipoHabitacion + ".");
        }
        
        Reserva nuevaReserva = new Reserva(cliente, habitacionDisponible, fechaInicio, fechaFin);
        reservas.add(nuevaReserva);
        System.out.println("Reserva exitosa para " + cliente.getNombre() + " en la habitacion " + habitacionDisponible.getNumero());
        return nuevaReserva;
    }

    @Override
    public boolean cancelarReserva(Reserva reserva) {
        if (reserva == null) {
            throw new ReservaException("La reserva a cancelar no puede ser nula.");
        }
        
        if (reservas.remove(reserva)) {
            System.out.println("Reserva cancelada para " + reserva.getCliente().getNombre());
            return true;
        }
        System.out.println("No se pudo cancelar la reserva.");
        return false;
    }
}
