package Pryct.estrategias;

import java.util.List;

import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;
import Pryct.modelo.ParAsociado;

public interface EstrategiaOrdenInventario {
    void ordenar(List<ParAsociado<Equipo, Mantenimiento>> asociaciones);
}
