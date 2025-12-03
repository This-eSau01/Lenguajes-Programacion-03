package Trivilines.modelo;
import java.util.Objects;

public class Categoria {

    private int id;
    private String nombre;
    private String descripcion;
    private String rutaIcono;

    public Categoria() {
    }

    public Categoria(int id, String nombre) {
        this(id, nombre, null, null);
    }

    public Categoria(int id, String nombre, String descripcion, String rutaIcono) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaIcono = rutaIcono;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaIcono() {
        return rutaIcono;
    }

    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return id == categoria.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
