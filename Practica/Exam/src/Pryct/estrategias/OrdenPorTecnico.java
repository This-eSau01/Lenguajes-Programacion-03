package Pryct.estrategias;

import Pryct.estrategias.EstrategiaOrdenInventario;
import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;
import Pryct.modelo.ParAsociado;

import java.util.Comparator;
import java.util.List;

public class OrdenPorTecnico implements EstrategiaOrdenInventario {

    @Override
    public void ordenar(List<ParAsociado<Equipo, Mantenimiento>> asociaciones) {
        asociaciones.sort(Comparator.comparing(
                a -> a.getSegundo() != null ? a.getSegundo().getTecnico() : ""
        ));
    }
}
