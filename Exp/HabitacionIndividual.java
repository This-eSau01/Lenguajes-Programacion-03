package hotel;

// Principio Abierto/Cerrado (OCP) y Sustitucion de Liskov (LSP)
// Se pueden agregar nuevos tipos de habitacion sin modificar el codigo de reserva.
public class HabitacionIndividual implements TipoHabitacion {
    @Override
    public String getNombre() { return "Individual"; }
    @Override
    public double getPrecioBase() { return 100.0; }
}