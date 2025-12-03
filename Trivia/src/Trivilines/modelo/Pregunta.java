package Trivilines.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Pregunta {

    private int id;
    private String texto;
    private List<String> opciones;
    private int indiceRespuestaCorrecta;
    private Categoria categoria;
    private int nivelDificultad;

    private int tiempoLimiteSegundos;

    public Pregunta() {
        this.opciones = new ArrayList<>();
    }

    public Pregunta(int id,
                    String texto,
                    List<String> opciones,
                    int indiceRespuestaCorrecta,
                    Categoria categoria,
                    int nivelDificultad,
                    int tiempoLimiteSegundos) {
        this.id = id;
        this.texto = texto;
        setOpciones(opciones);
        this.indiceRespuestaCorrecta = indiceRespuestaCorrecta;
        this.categoria = categoria;
        this.nivelDificultad = nivelDificultad;
        this.tiempoLimiteSegundos = tiempoLimiteSegundos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<String> getOpciones() {
        return Collections.unmodifiableList(opciones);
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = new ArrayList<>();
        if (opciones != null) {
            this.opciones.addAll(opciones);
        }
    }

    public int getIndiceRespuestaCorrecta() {
        return indiceRespuestaCorrecta;
    }

    public void setIndiceRespuestaCorrecta(int indiceRespuestaCorrecta) {
        this.indiceRespuestaCorrecta = indiceRespuestaCorrecta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(int nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public int getTiempoLimiteSegundos() {
        return tiempoLimiteSegundos;
    }

    public void setTiempoLimiteSegundos(int tiempoLimiteSegundos) {
        this.tiempoLimiteSegundos = tiempoLimiteSegundos;
    }

    public boolean esRespuestaCorrecta(int indiceRespuesta) {
        return indiceRespuesta == indiceRespuestaCorrecta;
    }

    public String getRespuestaCorrecta() {
        if (indiceRespuestaCorrecta >= 0 &&
                indiceRespuestaCorrecta < opciones.size()) {
            return opciones.get(indiceRespuestaCorrecta);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pregunta)) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", categoria=" + categoria +
                ", nivelDificultad=" + nivelDificultad +
                '}';
    }
}
