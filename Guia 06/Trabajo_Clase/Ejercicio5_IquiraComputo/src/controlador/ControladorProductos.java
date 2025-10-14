package controlador;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class ControladorProductos {
    private List<Producto> inventarioArequipa;
    private int contadorProductos;

    public ControladorProductos() {
        this.inventarioArequipa = new ArrayList<>();
        this.contadorProductos = 1;
        inicializarProductosDemo();
    }

    private void inicializarProductosDemo() {
        agregarProducto("Laptop Gamer Arequipa", "Laptop", "HP", 2899.00, 5, "Intel i7, 16GB RAM, RTX 3060");
        agregarProducto("Mouse Inalambrico", "Periferico", "Logitech", 89.90, 15, "Wireless, 2400 DPI");
        agregarProducto("Teclado Mecanico", "Periferico", "Redragon", 199.00, 8, "RGB, switches azules");
        agregarProducto("Monitor 24\"", "Monitor", "Samsung", 699.00, 6, "Full HD, 144Hz");
        agregarProducto("Impresora Laser", "Impresora", "Brother", 899.00, 3, "Blanco y negro, WiFi");
    }

    public boolean agregarProducto(String nombre, String categoria, String marca, 
                                  double precioSoles, int stockArequipa, String descripcionTecnica) {
        String codigo = "PROD" + String.format("%03d", contadorProductos++);
        Producto nuevoProducto = new Producto(codigo, nombre, categoria, marca, precioSoles, stockArequipa, descripcionTecnica);
        inventarioArequipa.add(nuevoProducto);
        return true;
    }

    public List<Producto> obtenerInventario() {
        return new ArrayList<>(inventarioArequipa);
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        return inventarioArequipa.stream()
                .filter(producto -> producto.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : inventarioArequipa) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    public boolean actualizarStock(String codigoProducto, int nuevoStock) {
        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto != null) {
            producto.setStockArequipa(nuevoStock);
            return true;
        }
        return false;
    }
}