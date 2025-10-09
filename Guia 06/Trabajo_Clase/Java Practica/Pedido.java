package default1;

public class Pedido {
    private String nombrePlato;
    private TipoPlato tipo;
    private EstadoPedido estado;

    public Pedido(String nombrePlato, TipoPlato tipo) {
        this.nombrePlato = nombrePlato;
        this.tipo = tipo;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Pedido(String nombrePlato, TipoPlato tipo, EstadoPedido estado) {
        this.nombrePlato = nombrePlato;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public TipoPlato getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlato tipo) {
        this.tipo = tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + estado + "] " + nombrePlato + " (" + tipo + ")";
    }
}