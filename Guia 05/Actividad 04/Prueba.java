package A4;

public class Prueba {
    public static void main(String[] args) {
        Pila<Integer> pila1 = new Pila<>(5);
        Pila<Integer> pila2 = new Pila<>(5);

        pila1.push(10);
        pila1.push(20);
        pila1.push(30);

        pila2.push(10);
        pila2.push(20);
        pila2.push(30);

        System.out.println("¿Pila1 es igual a Pila2? " + pila1.esIgual(pila2));

        pila2.pop();
        pila2.push(40);

        System.out.println("¿Pila1 es igual a Pila2? " + pila1.esIgual(pila2));
    }
}
