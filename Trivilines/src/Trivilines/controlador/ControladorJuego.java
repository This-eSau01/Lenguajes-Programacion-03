package Trivilines.controlador;

import Trivilines.modelo.EstadoJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;
import Trivilines.modelo.Pregunta;
import Trivilines.modelo.TipoComodin;
import Trivilines.repositorio.RepositorioJugadores;
import Trivilines.servicio.ServicioAudio;
import Trivilines.servicio.ServicioComodines;
import Trivilines.servicio.ServicioEstadisticas;
import Trivilines.servicio.ServicioJuego;

import java.util.List;


public class ControladorJuego {

    private final ServicioJuego servicioJuego;
    private final ServicioAudio servicioAudio;
    private final ServicioComodines servicioComodines;
    private final ServicioEstadisticas servicioEstadisticas;
    private final RepositorioJugadores repositorioJugadores;

    private Jugador jugadorActual;

    public ControladorJuego(ServicioJuego servicioJuego,
                            ServicioAudio servicioAudio,
                            ServicioComodines servicioComodines,
                            ServicioEstadisticas servicioEstadisticas,
                            RepositorioJugadores repositorioJugadores) {

        this.servicioJuego = servicioJuego;
        this.servicioAudio = servicioAudio;
        this.servicioComodines = servicioComodines;
        this.servicioEstadisticas = servicioEstadisticas;
        this.repositorioJugadores = repositorioJugadores;
    }

    /**
     * Busca un jugador por nombre. Si no existe, lo crea y lo persiste.
     *
     * @param nombre nombre del jugador.
     * @return jugador existente o recién creado.
     */
    public Jugador seleccionarOcrearJugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío");
        }

        Jugador jugador = repositorioJugadores.buscarPorNombre(nombre.trim());
        if (jugador == null) {
            jugador = new Jugador();
            jugador.setNombre(nombre.trim());
            jugador = repositorioJugadores.guardar(jugador);
        }

        this.jugadorActual = jugador;
        return jugador;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    /**
     * Inicia una nueva partida y devuelve la primera pregunta.
     *
     * @param categoriaId id de la categoría seleccionada.
     * @param nivel       nivel de dificultad (1, 2, 3...).
     */
    public Pregunta iniciarNuevaPartida(int categoriaId, int nivel) {
        if (jugadorActual == null) {
            throw new IllegalStateException("No hay jugador seleccionado");
        }

        servicioJuego.iniciarNuevaPartida(jugadorActual, categoriaId, nivel);
        servicioAudio.reproducirMusicaFondo();

        return servicioJuego.obtenerPreguntaActual();
    }

    /**
     * Devuelve la pregunta actual.
     */
    public Pregunta getPreguntaActual() {
        return servicioJuego.obtenerPreguntaActual();
    }

    /**
     * Pide al servicio la siguiente pregunta.
     */



    public Pregunta siguientePregunta() {
        return servicioJuego.siguientePregunta();
    }

    /**
     * Registra la respuesta del jugador a la pregunta actual.
     *
     * @param indiceRespuesta índice de la opción elegida.
     * @return true si fue correcta, false si no.
     */
    public boolean responderPregunta(int indiceRespuesta) {
        boolean correcta = servicioJuego.responderPregunta(indiceRespuesta);

        if (correcta) {
            servicioAudio.reproducirSonidoCorrecto();
        } else {
            servicioAudio.reproducirSonidoIncorrecto();
        }

        // Si la partida terminó como resultado de esta respuesta, detenemos la música
        if (servicioJuego.getEstadoJuego() == EstadoJuego.FINALIZADO) {
            servicioAudio.detenerMusicaFondo();
            if (servicioJuego.getPuntajeActual() > 0) {
                servicioAudio.reproducirSonidoVictoria();
            }
        }

        return correcta;
    }

    /**
     * Finaliza la partida manualmente (por ejemplo, si el jugador se rinde).
     */
    public void finalizarPartida(boolean victoria) {
        servicioJuego.finalizarPartida(victoria);
        servicioAudio.detenerMusicaFondo();
        if (victoria) {
            servicioAudio.reproducirSonidoVictoria();
        }
    }

    public int getPuntajeActual() {
        return servicioJuego.getPuntajeActual();
    }

    public int getNumeroPreguntaActual() {
        return servicioJuego.getNumeroPreguntaActual();
    }

    public EstadoJuego getEstadoJuego() {
        return servicioJuego.getEstadoJuego();
    }

    public Partida getPartidaActual() {
        return servicioJuego.getPartidaActual();
    }

    /**
     * Devuelve el ranking de jugadores por puntaje máximo.
     */
    public List<Jugador> obtenerRankingPuntajeMaximo(int limite) {
        return servicioEstadisticas.obtenerRankingPuntajeMaximo(limite);
    }

    /**
     * Devuelve el ranking de jugadores por puntaje promedio.
     */
    public List<Jugador> obtenerRankingPuntajePromedio(int limite) {
        return servicioEstadisticas.obtenerRankingPuntajePromedio(limite);
    }

    /**
     * Devuelve el porcentaje de victorias del jugador actual.
     */
    public double obtenerPorcentajeVictoriasJugadorActual() {
        if (jugadorActual == null) {
            return 0.0;
        }
        return servicioEstadisticas.obtenerPorcentajeVictoriasJugador(jugadorActual.getId());
    }

    /**
     * Intenta usar un comodín para el jugador actual.
     *
     * La lógica específica del comodín (qué hace en pantalla) la manejará
     * la vista con apoyo de ControladorComodines.
     */
    public boolean usarComodin(TipoComodin tipoComodin) {
        if (jugadorActual == null) {
            return false;
        }
        boolean usado = servicioComodines.usarComodin(jugadorActual, tipoComodin);
        if (usado) {
            servicioAudio.reproducirSonidoComodin();
        }
        return usado;
    }

    /**
     * Número total de partidas jugadas en el sistema (estadística general).
     */
    public int obtenerTotalPartidas() {
        return servicioEstadisticas.obtenerTotalPartidas();
    }
}