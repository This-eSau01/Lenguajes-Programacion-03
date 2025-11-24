package Pryct.modelo;

public class ParAsociado<T, U> {

    private final T primero;
    private final U segundo;

    public ParAsociado(T primero, U segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T getPrimero() { return primero; }
    public U getSegundo() { return segundo; }
}
