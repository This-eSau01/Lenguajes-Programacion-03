package hotel;

public class HabitacionNoEncontrada extends ReservaException {
    public HabitacionNoEncontrada(String mensaje) {
        super(mensaje);
    }
}