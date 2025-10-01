package A3;

public class Prueba {
    public static void main(String[] args) {
        System.out.println("Comparar enteros 10 y 10: " + IgualGenerico.esIgualA(10, 10));
        System.out.println("Comparar enteros 10 y 20: " + IgualGenerico.esIgualA(10, 20));

        System.out.println("Comparar null y 5: " + IgualGenerico.esIgualA(null, 5));
        System.out.println("Comparar null y null: " + IgualGenerico.esIgualA(null, null));

        Object obj1 = new Object();
        Object obj2 = obj1;
        Object obj3 = new Object();

        System.out.println("Comparar obj1 y obj2 (mismo objeto): " + IgualGenerico.esIgualA(obj1, obj2));
        System.out.println("Comparar obj1 y obj3 (objetos distintos): " + IgualGenerico.esIgualA(obj1, obj3));

        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = 200;

        System.out.println("Comparar num1 y num2 (100 y 100): " + IgualGenerico.esIgualA(num1, num2));
        System.out.println("Comparar num1 y num3 (100 y 200): " + IgualGenerico.esIgualA(num1, num3));

        String texto1 = "Hola";
        String texto2 = "Hola";
        String texto3 = "Adiós";

        System.out.println("Comparar texto1 y texto2 (Hola y Hola): " + IgualGenerico.esIgualA(texto1, texto2));
        System.out.println("Comparar texto1 y texto3 (Hola y Adiós): " + IgualGenerico.esIgualA(texto1, texto3));
    }
}
