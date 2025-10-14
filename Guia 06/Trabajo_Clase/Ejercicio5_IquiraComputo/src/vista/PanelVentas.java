package vista;

import controlador.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelVentas extends JPanel {
    private ControladorVentas controladorVentas;
    private ControladorProductos controladorProductos;
    private ControladorClientes controladorClientes;
    
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private JLabel etiquetaTotal;

    public PanelVentas(ControladorVentas controladorVentas, ControladorProductos controladorProductos, 
                      ControladorClientes controladorClientes) {
        this.controladorVentas = controladorVentas;
        this.controladorProductos = controladorProductos;
        this.controladorClientes = controladorClientes;
        
        configurarPanel();
        crearComponentes();
        actualizarCarrito();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void crearComponentes() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        JTextField campoCodigoProducto = new JTextField(10);
        JTextField campoCantidad = new JTextField(5);
        JTextField campoDniCliente = new JTextField(10);
        JButton botonAgregar = new JButton("Agregar al Carrito");
        
        botonAgregar.addActionListener(e -> {
            agregarProductoAlCarrito(
                campoCodigoProducto.getText(),
                campoCantidad.getText(),
                campoDniCliente.getText()
            );
        });
        
        panelBusqueda.add(new JLabel("Código Producto:"));
        panelBusqueda.add(campoCodigoProducto);
        panelBusqueda.add(new JLabel("Cantidad:"));
        panelBusqueda.add(campoCantidad);
        panelBusqueda.add(new JLabel("DNI Cliente:"));
        panelBusqueda.add(campoDniCliente);
        panelBusqueda.add(botonAgregar);
        
        JPanel panelControles = new JPanel(new FlowLayout());
        JButton botonProcesarVenta = new JButton("Procesar Venta");
        JButton botonLimpiarCarrito = new JButton("Limpiar Carrito");
        
        botonProcesarVenta.addActionListener(e -> procesarVenta());
        botonLimpiarCarrito.addActionListener(e -> limpiarCarrito());
        
        panelControles.add(botonProcesarVenta);
        panelControles.add(botonLimpiarCarrito);
        panelControles.add(new JLabel("Total Carrito:"));
        etiquetaTotal = new JLabel("S/ 0.00");
        panelControles.add(etiquetaTotal);
        
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelControles);
        
        String[] columnasCarrito = {"Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        modeloCarrito = new DefaultTableModel(columnasCarrito, 0);
        tablaCarrito = new JTable(modeloCarrito);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
    }

    private void agregarProductoAlCarrito(String codigoProducto, String textoCantidad, String dniCliente) {
        try {
            int cantidad = Integer.parseInt(textoCantidad);
            boolean exito = controladorVentas.agregarProductoAlCarrito(codigoProducto, cantidad, dniCliente);
            
            if (exito) {
                actualizarCarrito();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Producto no encontrado o stock insuficiente", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: La cantidad debe ser un número válido", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCarrito() {
        modeloCarrito.setRowCount(0);
        Carrito carrito = controladorVentas.getCarritoActual();
        
        for (ItemVenta item : carrito.getItemsCarrito()) {
            Object[] fila = {
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getPrecioUnitario(),
                item.getSubtotal()
            };
            modeloCarrito.addRow(fila);
        }
        
        etiquetaTotal.setText(String.format("S/ %.2f", carrito.calcularSubtotal()));
    }

    private void procesarVenta() {
        Venta venta = controladorVentas.procesarVenta();
        if (venta != null) {
            String resumen = venta.generarResumenVenta();
            JOptionPane.showMessageDialog(this, resumen, "Venta Procesada - IquiraComputo", 
                                        JOptionPane.INFORMATION_MESSAGE);
            actualizarCarrito();
        } else {
            JOptionPane.showMessageDialog(this, "Error: El carrito está vacío", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCarrito() {
        controladorVentas.limpiarCarrito();
        actualizarCarrito();
    }
}