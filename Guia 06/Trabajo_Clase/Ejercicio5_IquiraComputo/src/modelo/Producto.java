package modelo;


public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private String marca;
    private double precioSoles;
    private int stockArequipa;
    private String descripcionTecnica;

    public Producto(String codigo, String nombre, String categoria, String marca, 
                   double precioSoles, int stockArequipa, String descripcionTecnica) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.precioSoles = precioSoles;
        this.stockArequipa = stockArequipa;
        this.descripcionTecnica = descripcionTecnica;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getMarca() { return marca; }
    public double getPrecioSoles() { return precioSoles; }
    public int getStockArequipa() { return stockArequipa; }
    public String getDescripcionTecnica() { return descripcionTecnica; }

    public void setPrecioSoles(double precioSoles) { this.precioSoles = precioSoles; }
    public void setStockArequipa(int stockArequipa) { this.stockArequipa = stockArequipa; }

    public void reducirStock(int cantidadVendida) {
        this.stockArequipa -= cantidadVendida;
    }

    public boolean hayStockSuficiente(int cantidadRequerida) {
        return stockArequipa >= cantidadRequerida;
    }

    @Override
    public String toString() {
        return nombre + " - S/ " + precioSoles + " (Stock: " + stockArequipa + ")";
    }
}