package Pryct.modelo;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String codigo;
    private String nombre;
    private String tipo;
    private String estado;
    private double valor;
    private String descripcionTecnica;

    private final List<Mantenimiento> mantenimientos = new ArrayList<>();

    public Equipo(String codigo, String nombre, String tipo, String estado) {
        this(codigo, nombre, tipo, estado, 0.0, "");
    }

    public Equipo(String codigo, String nombre, String tipo, String estado,
                  double valor, String descripcionTecnica) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.valor = valor;
        this.descripcionTecnica = descripcionTecnica;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescripcionTecnica() { return descripcionTecnica; }
    public void setDescripcionTecnica(String descripcionTecnica) { this.descripcionTecnica = descripcionTecnica; }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo +
                ", Nombre: " + nombre +
                ", Tipo: " + tipo +
                ", Estado: " + estado +
                ", Valor: " + String.format("%.2f", valor);
    }
}
