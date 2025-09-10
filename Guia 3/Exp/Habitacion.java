package hotel;

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
