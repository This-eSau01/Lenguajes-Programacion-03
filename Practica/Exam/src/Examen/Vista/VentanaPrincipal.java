package Examen.Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Examen.ControladorInventario;
import Examen.Modelo.Equipo;
import Examen.Modelo.Mantenimiento;
import Examen.Modelo.ParAsociado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private final ControladorInventario controlador;
    private final JTextArea areaSalida;

    // Colores de la interfaz
    private final Color COLOR_FONDO = new Color(245, 247, 250);
    private final Color COLOR_PRIMARIO = new Color(33, 150, 243);    // azul
    private final Color COLOR_SECUNDARIO = new Color(25, 118, 210);  // azul oscuro
    private final Color COLOR_TEXTO = new Color(33, 33, 33);

    public VentanaPrincipal(ControladorInventario controlador) {
        this.controlador = controlador;
        this.areaSalida = new JTextArea();
        inicializarLookAndFeel();
        inicializarComponentes();
    }

    private void inicializarLookAndFeel() {
        // Fuente un poco más grande para todo
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TextArea.font", new Font("Consolas", Font.PLAIN, 12));
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Mantenimiento de Equipos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        // Encabezado
        JPanel header = crearHeader();
        add(header, BorderLayout.NORTH);

        // Menú lateral
        JPanel menuLateral = crearMenuLateral();
        add(menuLateral, BorderLayout.WEST);

        // Panel central (área de salida)
        JScrollPane centro = crearPanelCentral();
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel titulo = new JLabel("Módulo de Inventario y Mantenimiento");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel subtitulo = new JLabel("Gestione equipos, mantenimientos y registros de forma centralizada");
        subtitulo.setForeground(new Color(227, 242, 253));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPanel texto = new JPanel();
        texto.setBackground(COLOR_PRIMARIO);
        texto.setLayout(new BoxLayout(texto, BoxLayout.Y_AXIS));
        texto.add(titulo);
        texto.add(Box.createVerticalStrut(3));
        texto.add(subtitulo);

        header.add(texto, BorderLayout.CENTER);
        return header;
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(COLOR_SECUNDARIO);
        menu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        menu.setPreferredSize(new Dimension(230, getHeight()));

        JLabel lblMenu = new JLabel("Acciones");
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        menu.add(lblMenu);
        menu.add(Box.createVerticalStrut(10));
        menu.add(crearSeparador());

        JButton btnEquipo = crearBotonMenu("Registrar equipo", this::accionRegistrarEquipo);
        JButton btnMant = crearBotonMenu("Registrar mantenimiento", this::accionRegistrarMantenimiento);
        JButton btnListar = crearBotonMenu("Ver asociaciones equipo-mantenimiento", e -> mostrarAsociaciones());

        JButton btnGuardarArchivo = crearBotonMenu("Guardar información en archivo", e -> guardarArchivo());
        JButton btnCargarArchivo = crearBotonMenu("Cargar información desde archivo", e -> cargarArchivo());
        JButton btnGuardarBD = crearBotonMenu("Guardar información en base de datos", e -> guardarBD());

        JButton btnSalir = crearBotonMenu("Salir del sistema", e -> System.exit(0));
        btnSalir.setBackground(new Color(211, 47, 47)); // rojo suave

        menu.add(btnEquipo);
        menu.add(btnMant);
        menu.add(btnListar);
        menu.add(crearSeparador());
        menu.add(btnGuardarArchivo);
        menu.add(btnCargarArchivo);
        menu.add(btnGuardarBD);
        menu.add(Box.createVerticalGlue());
        menu.add(crearSeparador());
        menu.add(btnSalir);

        return menu;
    }

    private JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(187, 222, 251));
        return sep;
    }

    private JButton crearBotonMenu(String texto, java.util.function.Consumer<ActionEvent> accion) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        boton.addActionListener(e -> accion.accept(e));

        // efecto simple al pasar el mouse
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_SECUNDARIO.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO);
            }
        });

        return boton;
    }

    private JScrollPane crearPanelCentral() {
        areaSalida.setEditable(false);
        areaSalida.setBackground(Color.WHITE);
        areaSalida.setForeground(COLOR_TEXTO);
        areaSalida.setMargin(new Insets(10, 10, 10, 10));
        areaSalida.setText("Bienvenido.\n\n" +
                "Use el menú de la izquierda para registrar equipos, " +
                "registrar mantenimientos, listar asociaciones y " +
                "guardar la información en archivo o base de datos.\n");

        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scroll;
    }

    // ------------------- ACCIONES -------------------

    private void accionRegistrarEquipo(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Código:"));
        JTextField txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Nombre:"));
        JTextField txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Tipo:"));
        JTextField txtTipo = new JTextField();
        panel.add(txtTipo);

        panel.add(new JLabel("Estado:"));
        JTextField txtEstado = new JTextField();
        panel.add(txtEstado);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Registrar nuevo equipo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            controlador.registrarEquipo(
                    txtCodigo.getText().trim(),
                    txtNombre.getText().trim(),
                    txtTipo.getText().trim(),
                    txtEstado.getText().trim()
            );
            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente");
        }
    }

    private void accionRegistrarMantenimiento(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.add(new JLabel("Código del equipo:"));
        JTextField txtCodigo = new JTextField();
        panel.add(txtCodigo);

        panel.add(new JLabel("Fecha (AAAA-MM-DD):"));
        JTextField txtFecha = new JTextField(LocalDate.now().toString());
        panel.add(txtFecha);

        panel.add(new JLabel("Descripción:"));
        JTextField txtDescripcion = new JTextField();
        panel.add(txtDescripcion);

        panel.add(new JLabel("Técnico:"));
        JTextField txtTecnico = new JTextField();
        panel.add(txtTecnico);

        panel.add(new JLabel("Costo:"));
        JTextField txtCosto = new JTextField();
        panel.add(txtCosto);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Registrar mantenimiento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                double costo = Double.parseDouble(txtCosto.getText().trim());

                boolean ok = controlador.registrarMantenimiento(
                        txtCodigo.getText().trim(),
                        fecha,
                        txtDescripcion.getText().trim(),
                        txtTecnico.getText().trim(),
                        costo
                );

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Mantenimiento registrado y asociado");
                } else {
                    JOptionPane.showMessageDialog(this, "No existe un equipo con ese código");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
            }
        }
    }

    private void mostrarAsociaciones() {
        areaSalida.setText("");
        List<ParAsociado<Equipo, Mantenimiento>> lista = controlador.listarAsociaciones();
        if (lista.isEmpty()) {
            areaSalida.append("No hay registros de mantenimiento.\n");
            return;
        }
        for (ParAsociado<Equipo, Mantenimiento> p : lista) {
            areaSalida.append("EQUIPO\n");
            areaSalida.append(p.getPrimero().toString() + "\n\n");
            areaSalida.append("MANTENIMIENTO\n");
            areaSalida.append(p.getSegundo().toString() + "\n");
            areaSalida.append("------------------------------------------------------------\n");
        }
    }

    private void guardarArchivo() {
        try {
            controlador.guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "Información guardada en archivo");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void cargarArchivo() {
        try {
            controlador.cargarDesdeArchivo();
            JOptionPane.showMessageDialog(this, "Información cargada desde archivo");
            mostrarAsociaciones();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage());
        }
    }

    private void guardarBD() {
        try {
            controlador.guardarEnBaseDeDatos();
            JOptionPane.showMessageDialog(this, "Información guardada en la base de datos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en BD: " + ex.getMessage());
        }
    }
}

