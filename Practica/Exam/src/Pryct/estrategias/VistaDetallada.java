package Pryct.estrategias;

import Pryct.estrategias.EstrategiaPresentacionEquipo;
import Pryct.modelo.Equipo;

public class VistaDetallada implements EstrategiaPresentacionEquipo {

    @Override
    public String formatear(Equipo equipo) {
        return equipo.toString();
    }
}
