package Examen.Modelo;
import java.io.Serializable;


public class Equipo {
	private static final long serialVersionUID = 1L;
	
	private String codigo;
    private String nombre;
    private String tipo;
    private String estado;

    public Equipo(String codigo, String nombre, String tipo, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Equipo [" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ']';
    }
}