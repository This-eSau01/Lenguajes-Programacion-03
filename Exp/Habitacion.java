package hotel;

// Principio de Responsabilidad Unica (SRP)
// Solo se encarga de los datos de la habitacion.
public class Habitacion {
    private String numero;
    private TipoHabitacion tipo;
    private double precio;

    public Habitacion(String numero, TipoHabitacion tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = tipo.getPrecioBase();
    }

    public String getNumero() { return numero; }
    public TipoHabitacion getTipo() { return tipo; }
    public double getPrecio() { return precio; }
}