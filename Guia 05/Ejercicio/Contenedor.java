package Ejercice;

import java.util.ArrayList;
import java.util.List;

public class Contenedor<F, S> {
    private List<Par<F, S>> listaPares;

    public Contenedor() {
        listaPares = new ArrayList<>();
    }

    public void agregarPar(F primero, S segundo) {
        listaPares.add(new Par<>(primero, segundo));
    }

    public Par<F, S> obtenerPar(int indice) {
        return listaPares.get(indice);
    }

    public List<Par<F, S>> obtenerTodosLosPares() {
        return listaPares;
    }

    public void mostrarPares() {
        for (Par<F, S> par : listaPares) {
            System.out.println(par);
        }
    }
}
