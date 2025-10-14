package modelo;


public class ItemVenta {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioSoles();
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
}