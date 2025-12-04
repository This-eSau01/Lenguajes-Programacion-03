package Trivilines.modelo;
import Trivilines.vista.componentes.BarraProgreso;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;




public class Partida {

    private int id;
    private Jugador jugador;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private int preguntasRespondidas;
    private int respuestasCorrectas;
    private int respuestasIncorrectas;
    private int puntajeTotal;
    private boolean victoria;

    /**
     * Correctas por categoría (clave = categoría, valor = cantidad).
     */
    private Map<Categoria, Integer> correctasPorCategoria;

    public Partida() {
        this.fechaHoraInicio = LocalDateTime.now();
        this.correctasPorCategoria = new HashMap<>();
    }

    public Partida(int id, Jugador jugador) {
        this();
        this.id = id;
        this.jugador = jugador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public int getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public void setPreguntasRespondidas(int preguntasRespondidas) {
        this.preguntasRespondidas = preguntasRespondidas;
    }

    public int getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public void setRespuestasCorrectas(int respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public int getRespuestasIncorrectas() {
        return respuestasIncorrectas;
    }

    public void setRespuestasIncorrectas(int respuestasIncorrectas) {
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }

    public Map<Categoria, Integer> getCorrectasPorCategoria() {
        return correctasPorCategoria;
    }

    public void setCorrectasPorCategoria(Map<Categoria, Integer> correctasPorCategoria) {
        if (correctasPorCategoria == null) {
            this.correctasPorCategoria = new HashMap<>();
        } else {
            this.correctasPorCategoria = new HashMap<>(correctasPorCategoria);
        }
    }

    /**
     * Devuelve la duración de la partida en segundos.
     */
    public long getDuracionSegundos() {
        if (fechaHoraInicio == null || fechaHoraFin == null) {
            return 0;
        }
        return Duration.between(fechaHoraInicio, fechaHoraFin).getSeconds();
    }

    /**
     * Registra que el jugador respondió una pregunta.
     *
     * @param fueCorrecta true si fue correcta.
     * @param categoria   categoría de la pregunta.
     */
    public void registrarRespuesta(boolean fueCorrecta, Categoria categoria) {
        preguntasRespondidas++;
        if (fueCorrecta) {
            respuestasCorrectas++;
            if (categoria != null) {
                correctasPorCategoria.merge(categoria, 1, Integer::sum);
            }
        } else {
            respuestasIncorrectas++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partida)) return false;
        Partida partida = (Partida) o;
        return id == partida.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", jugador=" + jugador +
                ", puntajeTotal=" + puntajeTotal +
                ", victoria=" + victoria +
                '}';
    }
}
