package Examen;

import java.util.ArrayList;
import java.util.List;

import Examen.Modelo.Equipo;
import Examen.Modelo.Mantenimiento;
import Examen.Modelo.ParAsociado;

public class RepositorioAsociativo<T, U> {

    protected List<ParAsociado<T, U>> asociaciones = new ArrayList<>();

    public void agregar(T t, U u) {
        asociaciones.add(new ParAsociado<>(t, u));
    }

    public List<ParAsociado<T, U>> listar() {
        return new ArrayList<>(asociaciones);
    }

    public void limpiar() {
        asociaciones.clear();
    }

    public void agregarTodo(List<ParAsociado<T, U>> lista) {
        asociaciones.addAll(lista);
    }
}
