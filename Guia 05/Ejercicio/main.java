package Ejercice;

public class main {
    public static <F, S> void imprimirPar(Par<F, S> par) {
        System.out.println("Imprimiendo par: " + par);
    }

    public static void main(String[] args) {
        Par<String, Integer> par1 = new Par<>("Edad", 19);
        Par<Double, Boolean> par2 = new Par<>(9.8, true);
        Par<Persona, Integer> par3 = new Par<>(new Persona("Johnznnz"), 100);

        imprimirPar(par1);
        imprimirPar(par2);
        imprimirPar(par3);

        Contenedor<String, Integer> contenedor = new Contenedor<>();
        contenedor.agregarPar("Uno", 1);
        contenedor.agregarPar("Dos", 2);
        contenedor.agregarPar("Tres", 3);

        System.out.println("Contenido del contenedor:");
        contenedor.mostrarPares();
    }
}
