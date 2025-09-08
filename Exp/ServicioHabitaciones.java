package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Principio de Responsabilidad Unica (SRP) y Inversion de Dependencias (DIP)
// Se encarga de la logica de las habitaciones y depende de la interfaz TipoHabitacion.
public class ServicioHabitaciones implements GestionHabitaciones {
    private List<Habitacion> habitaciones = new ArrayList<>();

    public ServicioHabitaciones() {
        // Inicializar algunas habitaciones
        habitaciones.add(new Habitacion("101", new HabitacionIndividual()));
        habitaciones.add(new Habitacion("102", new HabitacionIndividual()));
    }

    @Override
    public Habitacion consultarDisponibilidad(String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        // Logica simplificada: solo encuentra una habitacion libre de ese tipo
        for (Habitacion h : habitaciones) {
            if (h.getTipo().getNombre().equals(tipoHabitacion)) {
                // Suponemos que la habitacion esta disponible
                System.out.println("Habitacion " + h.getNumero() + " del tipo " + tipoHabitacion + " esta disponible.");
                return h;
            }
        }
        return null;
    }

    @Override
    public void asignarHabitacion(Reserva reserva, Habitacion habitacion) {
        System.out.println("Asignando la habitacion " + habitacion.getNumero() + " a " + reserva.getCliente().getNombre());
    }

    @Override
    public void liberarHabitacion(Habitacion habitacion) {
        System.out.println("Liberando la habitacion " + habitacion.getNumero());
    }
}