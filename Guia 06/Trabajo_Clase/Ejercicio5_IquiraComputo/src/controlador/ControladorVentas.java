package controlador;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentas {
    private List<Venta> historialVentas;
    private ControladorProductos controladorProductos;
    private ControladorClientes controladorClientes;
    private Carrito carritoActual;
    private int contadorVentas;

    public ControladorVentas(ControladorProductos controladorProductos, ControladorClientes controladorClientes) {
        this.historialVentas = new ArrayList<>();
        this.controladorProductos = controladorProductos;
        this.controladorClientes = controladorClientes;
        this.carritoActual = new Carrito();
        this.contadorVentas = 1;
    }

    public boolean agregarProductoAlCarrito(String codigoProducto, int cantidad, String dniCliente) {
        Producto producto = controladorProductos.buscarProductoPorCodigo(codigoProducto);
        Cliente cliente = controladorClientes.buscarClientePorDni(dniCliente);

        if (producto == null || !producto.hayStockSuficiente(cantidad)) {
            return false;
        }

        if (cliente != null) {
            carritoActual.setClienteActual(cliente);
        }

        carritoActual.agregarProducto(producto, cantidad);
        return true;
    }

    public Venta procesarVenta() {
        if (carritoActual.estaVacio()) {
            return null;
        }

        String numeroBoleta = "B" + String.format("%04d", contadorVentas++);
        Venta nuevaVenta = new Venta(numeroBoleta, carritoActual.getClienteActual());

        for (ItemVenta item : carritoActual.getItemsCarrito()) {
            nuevaVenta.agregarItem(item.getProducto(), item.getCantidad());
            item.getProducto().reducirStock(item.getCantidad());
        }

        historialVentas.add(nuevaVenta);
        carritoActual.limpiarCarrito();
        return nuevaVenta;
    }

    public Carrito getCarritoActual() { return carritoActual; }
    public List<Venta> getHistorialVentas() { return new ArrayList<>(historialVentas); }

    public boolean eliminarProductoDelCarrito(String codigoProducto) {
        carritoActual.eliminarProducto(codigoProducto);
        return true;
    }

    public void limpiarCarrito() {
        carritoActual.limpiarCarrito();
    }
}