package Pryct.modelo;

import java.time.LocalDate;

public class Mantenimiento {

    private LocalDate fecha;
    private String descripcion;
    private String tecnico;
    private double costo;

    public Mantenimiento(LocalDate fecha, String descripcion, String tecnico, double costo) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tecnico = tecnico;
        this.costo = costo;
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    @Override
    public String toString() {
        return "Mantenimiento: " + fecha +
                ", " + descripcion +
                ", tecnico: " + tecnico +
                ", costo: " + String.format("%.2f", costo);
    }
}
