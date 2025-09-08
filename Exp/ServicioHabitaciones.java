package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServicioHabitaciones implements GestionHabitaciones {
    private List<Habitacion> habitaciones = new ArrayList<>();

    public ServicioHabitaciones() {

        habitaciones.add(new Habitacion("101", new HabitacionIndividual()));
        habitaciones.add(new Habitacion("102", new HabitacionIndividual()));
    }

    @Override
    public Habitacion consultarDisponibilidad(String tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        for (Habitacion h : habitaciones) {

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
