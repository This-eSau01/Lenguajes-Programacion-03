package Trivilines.modelo;
import java.util.Objects;


public class Comodin {

    private int id;
    private TipoComodin tipo;
    private boolean disponible;
    private int usosRestantes;

    public Comodin(int id, TipoComodin tipo) {
        this(id, tipo, true, 1);
    }

    public Comodin(int id, TipoComodin tipo, boolean disponible, int usosRestantes) {
        this.id = id;
        this.tipo = tipo;
        this.disponible = disponible;
        this.usosRestantes = usosRestantes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoComodin getTipo() {
        return tipo;
    }

    public void setTipo(TipoComodin tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible && usosRestantes > 0;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getUsosRestantes() {
        return usosRestantes;
    }

    public void setUsosRestantes(int usosRestantes) {
        this.usosRestantes = usosRestantes;
    }

    public boolean usar() {
        if (!isDisponible()) {
            return false;
        }
        usosRestantes--;
        if (usosRestantes <= 0) {
            disponible = false;
        }
        return true;
    }

    public String getNombreVisible() {
        return tipo != null ? tipo.getNombreVisible() : "";
    }

    public String getDescripcion() {
        return tipo != null ? tipo.getDescripcion() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comodin)) return false;
        Comodin comodin = (Comodin) o;
        return id == comodin.id &&
                tipo == comodin.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo);
    }

    @Override
    public String toString() {
        return "Comodin{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", disponible=" + disponible +
                ", usosRestantes=" + usosRestantes +
                '}';
    }
}
