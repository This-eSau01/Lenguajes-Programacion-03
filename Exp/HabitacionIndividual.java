package hotel;

public class HabitacionIndividual implements TipoHabitacion {
    @Override
    public String getNombre() { return "Individual"; }
    @Override
    public double getPrecioBase() { return 100.0; }
}
