package Trivilines.servicio;


import Trivilines.modelo.Comodin;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Maneja el estado de los comodines por jugador en memoria.
 */
public class ServicioComodinesImpl implements ServicioComodines {

    private static final int USOS_INICIALES_5050 = 1;
    private static final int USOS_INICIALES_DOBLE_OPORTUNIDAD = 1;
    private static final int USOS_INICIALES_BOMBA = 1;
    private static final int USOS_INICIALES_TIEMPO_EXTRA = 1;

    private static final int SEGUNDOS_EXTRA_TIEMPO = 5;
    private static final int MULTIPLICADOR_PUNTAJE_BOMBA = 2;

    private final Map<Integer, List<Comodin>> comodinesPorJugador = new HashMap<>();

    @Override
    public void asignarComodinesIniciales(Jugador jugador) {
        if (jugador == null) {
            return;
        }
        int jugadorId = jugador.getId();

        List<Comodin> lista = new ArrayList<>();
        int idSecuencial = 1;

        lista.add(new Comodin(idSecuencial++, TipoComodin.CINCUENTA_CINCUENTA, true, USOS_INICIALES_5050));
        lista.add(new Comodin(idSecuencial++, TipoComodin.DOBLE_OPORTUNIDAD, true, USOS_INICIALES_DOBLE_OPORTUNIDAD));
        lista.add(new Comodin(idSecuencial++, TipoComodin.BOMBA, true, USOS_INICIALES_BOMBA));
        lista.add(new Comodin(idSecuencial++, TipoComodin.TIEMPO_EXTRA, true, USOS_INICIALES_TIEMPO_EXTRA));

        comodinesPorJugador.put(jugadorId, lista);
    }

    @Override
    public List<Comodin> obtenerComodinesJugador(Jugador jugador) {
        if (jugador == null) {
            return Collections.emptyList();
        }
        return comodinesPorJugador.getOrDefault(jugador.getId(), Collections.emptyList())
                .stream()
                .map(c -> new Comodin(c.getId(), c.getTipo(), c.isDisponible(), c.getUsosRestantes()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean usarComodin(Jugador jugador, TipoComodin tipo) {
        if (jugador == null || tipo == null) {
            return false;
        }
        List<Comodin> lista = comodinesPorJugador.get(jugador.getId());
        if (lista == null) {
            return false;
        }

        for (Comodin c : lista) {
            if (c.getTipo() == tipo && c.isDisponible()) {
                return c.usar();
            }
        }
        return false;
    }

    @Override
    public List<Integer> obtenerIndicesAEliminar5050(Pregunta pregunta) {
        if (pregunta == null || pregunta.getOpciones().size() < 4) {
            return Collections.emptyList();
        }

        int indiceCorrecto = pregunta.getIndiceRespuestaCorrecta();
        List<Integer> indicesIncorrectos = new ArrayList<>();
        for (int i = 0; i < pregunta.getOpciones().size(); i++) {
            if (i != indiceCorrecto) {
                indicesIncorrectos.add(i);
            }
        }

        Collections.shuffle(indicesIncorrectos);
        return indicesIncorrectos.stream()
                .limit(2)
                .collect(Collectors.toList());
    }

    @Override
    public int obtenerMultiplicadorPuntajeBomba() {
        return MULTIPLICADOR_PUNTAJE_BOMBA;
    }

    @Override
    public int obtenerSegundosExtraTiempo() {
        return SEGUNDOS_EXTRA_TIEMPO;
    }
}
