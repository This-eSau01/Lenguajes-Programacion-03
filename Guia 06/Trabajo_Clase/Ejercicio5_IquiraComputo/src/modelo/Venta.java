package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String numeroBoleta;
    private LocalDateTime fechaHoraVenta;
    private Cliente clienteComprador;
    private List<ItemVenta> itemsVendidos;
    private double subtotalSoles;
    private double igvAplicado;
    private double totalPagarSoles;

    public Venta(String numeroBoleta, Cliente clienteComprador) {
        this.numeroBoleta = numeroBoleta;
        this.fechaHoraVenta = LocalDateTime.now();
        this.clienteComprador = clienteComprador;
        this.itemsVendidos = new ArrayList<>();
        this.subtotalSoles = 0.0;
        this.igvAplicado = 0.18;
        this.totalPagarSoles = 0.0;
    }

    public void agregarItem(Producto producto, int cantidad) {
        ItemVenta item = new ItemVenta(producto, cantidad);
        itemsVendidos.add(item);
        calcularTotales();
    }

    private void calcularTotales() {
        subtotalSoles = itemsVendidos.stream()
                .mapToDouble(ItemVenta::getSubtotal)
                .sum();
        totalPagarSoles = subtotalSoles * (1 + igvAplicado);
    }

    public String getNumeroBoleta() { return numeroBoleta; }
    public LocalDateTime getFechaHoraVenta() { return fechaHoraVenta; }
    public Cliente getClienteComprador() { return clienteComprador; }
    public List<ItemVenta> getItemsVendidos() { return new ArrayList<>(itemsVendidos); }
    public double getSubtotalSoles() { return subtotalSoles; }
    public double getIgvAplicado() { return igvAplicado; }
    public double getTotalPagarSoles() { return totalPagarSoles; }

    public String generarResumenVenta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder resumen = new StringBuilder();
        resumen.append("BOLETA: ").append(numeroBoleta).append("\n");
        resumen.append("IquiraComputo - Arequipa\n");
        resumen.append("Fecha: ").append(fechaHoraVenta.format(formatter)).append("\n");
        resumen.append("Cliente: ").append(clienteComprador.getNombresCompletos()).append("\n");
        resumen.append("DNI: ").append(clienteComprador.getDni()).append("\n");
        resumen.append("\nProductos:\n");
        
        for (ItemVenta item : itemsVendidos) {
            resumen.append(String.format("- %s x%d: S/ %.2f\n", 
                item.getProducto().getNombre(), 
                item.getCantidad(), 
                item.getSubtotal()));
        }
        
        resumen.append(String.format("\nSubtotal: S/ %.2f", subtotalSoles));
        resumen.append(String.format("\nIGV (18%%): S/ %.2f", subtotalSoles * igvAplicado));
        resumen.append(String.format("\nTOTAL: S/ %.2f", totalPagarSoles));
        
        return resumen.toString();
    }
}