package Pryct.estrategias;

import Pryct.estrategias.EstrategiaPresentacionEquipo;
import Pryct.modelo.Equipo;

public class VistaTecnica implements EstrategiaPresentacionEquipo {

    @Override
    public String formatear(Equipo equipo) {
        return equipo.toString() + ", Desc. técnica: " + equipo.getDescripcionTecnica();
    }
}
