package A2;

public class Pila<E> {
    private final int tamano;
    private int superior;
    private E[] elementos;

    public Pila() {
        this(10);
    }

    public Pila(int s) {
        tamano = s > 0 ? s : 10;
        superior = -1;
        elementos = (E[]) new Object[tamano];
    }

    public void push(E valor) {
        if (superior == tamano - 1)
            throw new ExcepcionPilaLlena("La pila está llena, no se puede meter " + valor);
        elementos[++superior] = valor;
    }

    public E pop() {
        if (superior == -1)
            throw new ExcepcionPilaVacia("La pila está vacía, no se puede sacar");
        return elementos[superior--];
    }

    public boolean contains(E valor) {
        for (int i = superior; i >= 0; i--) {
            if (elementos[i].equals(valor)) {
                return true;
            }
        }
        return false;
    }
}
