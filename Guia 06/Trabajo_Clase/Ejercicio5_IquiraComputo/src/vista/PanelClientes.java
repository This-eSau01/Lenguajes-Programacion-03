package vista;

import controlador.ControladorClientes;
import modelo.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelClientes extends JPanel {
    private ControladorClientes controlador;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public PanelClientes(ControladorClientes controlador) {
        this.controlador = controlador;
        configurarPanel();
        crearComponentes();
        cargarClientes();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void crearComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout());
        JButton botonNuevoCliente = new JButton("Nuevo Cliente");
        JButton botonActualizar = new JButton("Actualizar Lista");
        
        botonNuevoCliente.addActionListener(e -> mostrarDialogoNuevoCliente());
        botonActualizar.addActionListener(e -> cargarClientes());
        
        panelSuperior.add(botonNuevoCliente);
        panelSuperior.add(botonActualizar);
        
        String[] columnas = {"DNI", "Nombres Completos", "Correo", "Celular", "Distrito"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = controlador.obtenerTodosClientes();
        
        for (Cliente cliente : clientes) {
            Object[] fila = {
                cliente.getDni(),
                cliente.getNombresCompletos(),
                cliente.getCorreoElectronico(),
                cliente.getCelular(),
                cliente.getDistritoArequipa()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void mostrarDialogoNuevoCliente() {
        JTextField campoDni = new JTextField();
        JTextField campoNombres = new JTextField();
        JTextField campoCorreo = new JTextField();
        JTextField campoCelular = new JTextField();
        JTextField campoDistrito = new JTextField();
        JTextField campoDireccion = new JTextField();

        Object[] campos = {
            "DNI:", campoDni,
            "Nombres Completos:", campoNombres,
            "Correo Electrónico:", campoCorreo,
            "Celular:", campoCelular,
            "Distrito Arequipa:", campoDistrito,
            "Dirección Completa:", campoDireccion
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, 
                "Registrar Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
        
        if (resultado == JOptionPane.OK_OPTION) {
            boolean exito = controlador.registrarCliente(
                campoDni.getText(),
                campoNombres.getText(),
                campoCorreo.getText(),
                campoCelular.getText(),
                campoDistrito.getText(),
                campoDireccion.getText()
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error: El DNI ya está registrado", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}