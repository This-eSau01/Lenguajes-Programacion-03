package main;

import vista.*;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controlador.*;


public class MainFrame extends JFrame {
    private ControladorProductos controladorProductos;
    private ControladorClientes controladorClientes;
    private ControladorVentas controladorVentas;
    
    private JTabbedPane tabbedPane;
    private PanelProductos panelProductos;
    private PanelClientes panelClientes;
    private PanelVentas panelVentas;

    public MainFrame() {
        inicializarControladores();
        configurarVentana();
        crearComponentes();
        agregarComponentes();
    }

    private void inicializarControladores() {
        controladorProductos = new ControladorProductos();
        controladorClientes = new ControladorClientes();
        controladorVentas = new ControladorVentas(controladorProductos, controladorClientes);
    }

    private void configurarVentana() {
        setTitle("IquiraComputo - Sistema de Gestión Arequipa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("icono.png").getImage());
    }

    private void crearComponentes() {
        tabbedPane = new JTabbedPane();
        panelProductos = new PanelProductos(controladorProductos);
        panelClientes = new PanelClientes(controladorClientes);
        panelVentas = new PanelVentas(controladorVentas, controladorProductos, controladorClientes);
    }

    private void agregarComponentes() {
        tabbedPane.addTab("Productos", panelProductos);
        tabbedPane.addTab("Clientes", panelClientes);
        tabbedPane.addTab("Ventas", panelVentas);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}