package A2;

public class Prueba {
    public static void main(String[] args) {
        Pila<Integer> pilaEnteros = new Pila<>(5);

        pilaEnteros.push(10);
        pilaEnteros.push(20);
        pilaEnteros.push(30);

        System.out.println("¿La pila contiene 20? " + pilaEnteros.contains(20));
        System.out.println("¿La pila contiene 40? " + pilaEnteros.contains(40));

        System.out.println("Elemento sacado: " + pilaEnteros.pop());
        System.out.println("¿La pila contiene 30? " + pilaEnteros.contains(30));
    }
}
