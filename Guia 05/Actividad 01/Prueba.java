package A1;

public class Prueba {

    public static <T> void imprimirArreglo(T[] arreglo) {
        for (T elemento : arreglo) {
            System.out.printf("%s ", elemento);
        }
        System.out.println();
    }

    public static <T> int imprimirArreglo(T[] arreglo, int indiceInferior, int indiceSuperior) {
        if (indiceInferior < 0 || indiceSuperior >= arreglo.length || indiceInferior >= indiceSuperior) {
            throw new ExcepcionIndiceInvalido("Los índices son inválidos");
        }

        int cantidadElementos = 0;
        for (int i = indiceInferior; i <= indiceSuperior; i++) {
            System.out.printf("%s ", arreglo[i]);
            cantidadElementos++;
        }
        System.out.println();
        return cantidadElementos;
    }

    public static void main(String[] args) {
        Integer[] numeros = { 1, 2, 3, 4, 5, 6 };
        Double[] decimales = { 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7 };
        Character[] letras = { 'H', 'O', 'L', 'A' };

        System.out.println("El arreglo de números contiene:");
        imprimirArreglo(numeros);

        System.out.println("\nEl arreglo de decimales contiene:");
        imprimirArreglo(decimales);

        System.out.println("\nEl arreglo de letras contiene:");
        imprimirArreglo(letras);

        System.out.println("\nSubarreglo de números [1,4]:");
        int cant1 = imprimirArreglo(numeros, 1, 4);
        System.out.println("Cantidad de elementos impresos: " + cant1);

        System.out.println("\nSubarreglo de decimales [2,5]:");
        int cant2 = imprimirArreglo(decimales, 2, 5);
        System.out.println("Cantidad de elementos impresos: " + cant2);

        System.out.println("\nSubarreglo de letras [0,2]:");
        int cant3 = imprimirArreglo(letras, 0, 2);
        System.out.println("Cantidad de elementos impresos: " + cant3);
    }
}
