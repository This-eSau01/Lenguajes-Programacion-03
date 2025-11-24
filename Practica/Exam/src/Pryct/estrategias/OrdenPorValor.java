package Pryct.estrategias;

import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;
import Pryct.modelo.ParAsociado;

import java.util.Comparator;
import java.util.List;

public class OrdenPorValor implements EstrategiaOrdenInventario {

    @Override
    public void ordenar(List<ParAsociado<Equipo, Mantenimiento>> asociaciones) {
        asociaciones.sort(Comparator.comparingDouble(a -> a.getPrimero().getValor()));
    }
}
