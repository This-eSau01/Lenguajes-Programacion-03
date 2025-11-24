package Pryct.estrategias;

import Pryct.modelo.Equipo;

public class VistaCompacta implements EstrategiaPresentacionEquipo {

    @Override
    public String formatear(Equipo equipo) {
        return equipo.getNombre();
    }
}
