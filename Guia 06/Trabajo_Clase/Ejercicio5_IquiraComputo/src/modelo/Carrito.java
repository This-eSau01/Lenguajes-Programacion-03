package modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemVenta> itemsCarrito;
    private Cliente clienteActual;

    public Carrito() {
        this.itemsCarrito = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemVenta item : itemsCarrito) {
            if (item.getProducto().getCodigo().equals(producto.getCodigo())) {
                return;
            }
        }
        itemsCarrito.add(new ItemVenta(producto, cantidad));
    }

    public void eliminarProducto(String codigoProducto) {
        itemsCarrito.removeIf(item -> item.getProducto().getCodigo().equals(codigoProducto));
    }

    public void limpiarCarrito() {
        itemsCarrito.clear();
        clienteActual = null;
    }

    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public List<ItemVenta> getItemsCarrito() { return new ArrayList<>(itemsCarrito); }
    public Cliente getClienteActual() { return clienteActual; }
    public boolean estaVacio() { return itemsCarrito.isEmpty(); }

    public double calcularSubtotal() {
        return itemsCarrito.stream()
                .mapToDouble(ItemVenta::getSubtotal)
                .sum();
    }
}