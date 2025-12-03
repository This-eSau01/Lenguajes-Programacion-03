package Trivilines.modelo;

import java.time.LocalDateTime;
import java.util.Objects;


public class Jugador {

    private int id;
    private String nombre;
    private LocalDateTime fechaRegistro;
    private int partidasJugadas;
    private int partidasGanadas;
    private int puntajeMaximo;
    private int puntajeTotalAcumulado;

    public Jugador() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public Jugador(int id, String nombre) {
        this(id, nombre, LocalDateTime.now(), 0, 0, 0, 0);
    }

    public Jugador(int id,
                   String nombre,
                   LocalDateTime fechaRegistro,
                   int partidasJugadas,
                   int partidasGanadas,
                   int puntajeMaximo,
                   int puntajeTotalAcumulado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDateTime.now();
        this.partidasJugadas = partidasJugadas;
        this.partidasGanadas = partidasGanadas;
        this.puntajeMaximo = puntajeMaximo;
        this.puntajeTotalAcumulado = puntajeTotalAcumulado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public int getPuntajeTotalAcumulado() {
        return puntajeTotalAcumulado;
    }

    public void setPuntajeTotalAcumulado(int puntajeTotalAcumulado) {
        this.puntajeTotalAcumulado = puntajeTotalAcumulado;
    }

    public double getPuntajePromedio() {
        if (partidasJugadas == 0) {
            return 0.0;
        }
        return (double) puntajeTotalAcumulado / partidasJugadas;
    }

    public void registrarResultadoPartida(int puntajeObtenido, boolean gano) {
        partidasJugadas++;
        if (gano) {
            partidasGanadas++;
        }
        puntajeTotalAcumulado += puntajeObtenido;
        if (puntajeObtenido > puntajeMaximo) {
            puntajeMaximo = puntajeObtenido;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return id == jugador.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
