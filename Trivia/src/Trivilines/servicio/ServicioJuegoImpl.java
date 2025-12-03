package Trivilines.servicio;
import Trivilines.modelo.EstadoJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;
import Trivilines.modelo.Pregunta;
import Trivilines.repositorio.RepositorioJugadores;
import Trivilines.repositorio.RepositorioPartidas;
import Trivilines.repositorio.RepositorioPreguntas;

import java.time.LocalDateTime;

public class ServicioJuegoImpl implements ServicioJuego {

    private static final int MAX_PREGUNTAS_POR_PARTIDA = 10;

    private final RepositorioPreguntas repositorioPreguntas;
    private final RepositorioJugadores repositorioJugadores;
    private final RepositorioPartidas repositorioPartidas;
    private final ServicioComodines servicioComodines;

    private EstadoJuego estadoJuego = EstadoJuego.MENU_PRINCIPAL;
    private Partida partidaActual;
    private Pregunta preguntaActual;
    private Jugador jugadorActual;

    private int categoriaActualId;
    private int nivelActual;

    private int puntajeActual;
    private int numeroPreguntaActual;

    public ServicioJuegoImpl(RepositorioPreguntas repositorioPreguntas,
                             RepositorioJugadores repositorioJugadores,
                             RepositorioPartidas repositorioPartidas,
                             ServicioComodines servicioComodines) {
        this.repositorioPreguntas = repositorioPreguntas;
        this.repositorioJugadores = repositorioJugadores;
        this.repositorioPartidas = repositorioPartidas;
        this.servicioComodines = servicioComodines;
    }

    @Override
    public void iniciarNuevaPartida(Jugador jugador, int categoriaId, int nivel) {
        this.jugadorActual = jugador;
        this.categoriaActualId = categoriaId;
        this.nivelActual = nivel;

        this.puntajeActual = 0;
        this.numeroPreguntaActual = 0;

        this.partidaActual = new Partida();
        partidaActual.setJugador(jugadorActual);
        partidaActual.setFechaHoraInicio(LocalDateTime.now());
        partidaActual.setPuntajeTotal(0);
        partidaActual.setPreguntasRespondidas(0);
        partidaActual.setRespuestasCorrectas(0);
        partidaActual.setRespuestasIncorrectas(0);
        partidaActual.setVictoria(false);

        this.estadoJuego = EstadoJuego.EN_JUEGO;

        if (servicioComodines != null && jugadorActual != null) {
            servicioComodines.asignarComodinesIniciales(jugadorActual);
        }

        siguientePregunta();
    }

    @Override
    public Pregunta obtenerPreguntaActual() {
        return preguntaActual;
    }

    @Override
    public Pregunta siguientePregunta() {
        if (estadoJuego != EstadoJuego.EN_JUEGO) {
            return null;
        }

        if (numeroPreguntaActual >= MAX_PREGUNTAS_POR_PARTIDA) {
            finalizarPartida(puntajeActual > 0);
            return null;
        }

        numeroPreguntaActual++;

        preguntaActual = repositorioPreguntas.obtenerPreguntaAleatoria(categoriaActualId, nivelActual);

        if (preguntaActual == null) {
            finalizarPartida(puntajeActual > 0);
        }

        return preguntaActual;
    }

    @Override
    public boolean responderPregunta(int indiceRespuesta) {
        if (preguntaActual == null || estadoJuego != EstadoJuego.EN_JUEGO) {
            return false;
        }

        boolean correcta = preguntaActual.esRespuestaCorrecta(indiceRespuesta);

        if (partidaActual != null) {
            partidaActual.registrarRespuesta(correcta, preguntaActual.getCategoria());
        }

        if (correcta) {
            int puntos = calcularPuntajePregunta(preguntaActual);
            puntajeActual += puntos;
            if (partidaActual != null) {
                partidaActual.setPuntajeTotal(puntajeActual);
            }
        }

        if (numeroPreguntaActual >= MAX_PREGUNTAS_POR_PARTIDA) {
            finalizarPartida(puntajeActual > 0);
        }

        return correcta;
    }

    private int calcularPuntajePregunta(Pregunta pregunta) {
        int base = 10;
        int multiplicador = Math.max(1, pregunta.getNivelDificultad());
        return base * multiplicador;
    }

    @Override
    public void finalizarPartida(boolean victoria) {
        if (partidaActual == null || estadoJuego == EstadoJuego.FINALIZADO) {
            return;
        }

        partidaActual.setFechaHoraFin(LocalDateTime.now());
        partidaActual.setPuntajeTotal(puntajeActual);
        partidaActual.setVictoria(victoria);
        if (preguntaActual != null) {
            partidaActual.setPreguntasRespondidas(partidaActual.getRespuestasCorrectas()
                    + partidaActual.getRespuestasIncorrectas());
        }

        repositorioPartidas.guardar(partidaActual);

        if (jugadorActual != null) {
            jugadorActual.registrarResultadoPartida(puntajeActual, victoria);
            repositorioJugadores.guardar(jugadorActual);
        }

        estadoJuego = EstadoJuego.FINALIZADO;
    }

    @Override
    public int getPuntajeActual() {
        return puntajeActual;
    }

    @Override
    public int getNumeroPreguntaActual() {
        return numeroPreguntaActual;
    }

    @Override
    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    @Override
    public Partida getPartidaActual() {
        return partidaActual;
    }
}
