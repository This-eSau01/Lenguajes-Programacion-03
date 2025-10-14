package vista;

import controlador.ControladorProductos;
import modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProductos extends JPanel {
    private ControladorProductos controlador;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;

    public PanelProductos(ControladorProductos controlador) {
        this.controlador = controlador;
        configurarPanel();
        crearComponentes();
        cargarProductos();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void crearComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        campoBusqueda = new JTextField(20);
        JButton botonBuscar = new JButton("Buscar Producto");
        
        botonBuscar.addActionListener(e -> buscarProductos());
        
        panelSuperior.add(new JLabel("Buscar producto:"), BorderLayout.WEST);
        panelSuperior.add(campoBusqueda, BorderLayout.CENTER);
        panelSuperior.add(botonBuscar, BorderLayout.EAST);
        
        String[] columnas = {"Código", "Nombre", "Categoría", "Marca", "Precio (S/)", "Stock Arequipa"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<Producto> productos = controlador.obtenerInventario();
        
        for (Producto producto : productos) {
            Object[] fila = {
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getMarca(),
                producto.getPrecioSoles(),
                producto.getStockArequipa()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscarProductos() {
        String textoBusqueda = campoBusqueda.getText().trim();
        if (textoBusqueda.isEmpty()) {
            cargarProductos();
            return;
        }
        
        modeloTabla.setRowCount(0);
        List<Producto> resultados = controlador.buscarProductosPorNombre(textoBusqueda);
        
        for (Producto producto : resultados) {
            Object[] fila = {
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getMarca(),
                producto.getPrecioSoles(),
                producto.getStockArequipa()
            };
            modeloTabla.addRow(fila);
        }
        
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre", 
                                        "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}