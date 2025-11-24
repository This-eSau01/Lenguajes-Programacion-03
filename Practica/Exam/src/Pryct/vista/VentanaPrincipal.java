package Pryct.vista;

import Pryct.controlador.ControladorInventario;
import Pryct.estrategias.*;
import Pryct.notificaciones.EventoInventario;
import Pryct.notificaciones.GestorNotificacionesInventario;
import Pryct.notificaciones.ObservadorInventario;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class VentanaPrincipal extends JFrame implements ObservadorInventario {

    private final ControladorInventario controlador;
    private final GestorNotificacionesInventario gestor;

    private final JTextArea areaSalida = new JTextArea();
    private final JLabel lblEstado = new JLabel("Listo");

    private JComboBox<String> cboOrden;
    private JComboBox<String> cboVista;
    private JCheckBox chkRegistrar;
    private JCheckBox chkMostrar;

    private boolean mostrarEventosEnPanel = false;

    // Colores base (puedes ajustarlos si quieres otro estilo)
    private static final Color COLOR_FONDO_MENU = new Color(6, 82, 221);
    private static final Color COLOR_BOTON = new Color(52, 152, 219);
    private static final Color COLOR_BOTON_HOVER = new Color(41, 128, 185);
    private static final Color COLOR_TEXTO_MENU = Color.WHITE;

    public VentanaPrincipal(ControladorInventario controlador,
                            GestorNotificacionesInventario gestor) {
        this.controlador = controlador;
        this.gestor = gestor;
        this.gestor.suscribir(this);

        setTitle("Sistema de Mantenimiento de Equipos");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaSalida.setMargin(new Insets(10, 10, 10, 10));
        areaSalida.setLineWrap(true);
        areaSalida.setWrapStyleWord(true);

        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        construirUI();
    }

    private void construirUI() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(COLOR_FONDO_MENU);
        panelMenu.setPreferredSize(new Dimension(260, 100));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelMenu, BorderLayout.WEST);

        JLabel titulo = new JLabel("<html><b>Módulo de Inventario<br>y Mantenimiento</b></html>");
        titulo.setForeground(COLOR_TEXTO_MENU);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenu.add(titulo);
        panelMenu.add(Box.createVerticalStrut(15));

        panelMenu.add(crearBotonAccion("Registrar equipo", this::accionRegistrarEquipo));
        panelMenu.add(crearBotonAccion("Registrar mantenimiento", this::accionRegistrarMantenimiento));
        panelMenu.add(crearBotonAccion("Ver inventario", this::accionVerInventario));
        panelMenu.add(crearBotonAccion("Guardar en archivo", this::accionGuardarArchivo));
        panelMenu.add(crearBotonAccion("Cargar desde archivo", this::accionCargarArchivo));
        panelMenu.add(crearBotonAccion("Guardar en BD", this::accionGuardarBD));
        panelMenu.add(crearBotonAccion("Cargar desde BD", this::accionCargarBD));
        panelMenu.add(crearBotonAccion("Limpiar inventario", this::accionLimpiar));
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(crearBotonAccion("Salir", this::accionSalir));

        panelMenu.add(Box.createVerticalStrut(15));

        JLabel lblOrden = new JLabel("Orden inventario:");
        lblOrden.setForeground(COLOR_TEXTO_MENU);
        lblOrden.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenu.add(lblOrden);

        cboOrden = new JComboBox<>(new String[]{
                "Por técnico", "Por valor", "Por fecha mantenimiento"
        });
        estilizarCombo(cboOrden);
        // Cuando cambie la selección, regenerar el listado
        cboOrden.addActionListener(e -> accionVerInventario());
        panelMenu.add(cboOrden);

        panelMenu.add(Box.createVerticalStrut(10));

        JLabel lblVista = new JLabel("Vista de equipos:");
        lblVista.setForeground(COLOR_TEXTO_MENU);
        lblVista.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelMenu.add(lblVista);

        cboVista = new JComboBox<>(new String[]{
                "Compacta", "Detallada", "Técnica"
        });
        estilizarCombo(cboVista);
        // Cuando cambie la selección, regenerar el listado
        cboVista.addActionListener(e -> accionVerInventario());
        panelMenu.add(cboVista);

        panelMenu.add(Box.createVerticalStrut(10));

        chkRegistrar = new JCheckBox("Registrar eventos en log");
        chkRegistrar.setOpaque(false);
        chkRegistrar.setForeground(COLOR_TEXTO_MENU);
        chkRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkRegistrar.addActionListener(e ->
                controlador.activarRegistroEventos(chkRegistrar.isSelected()));
        panelMenu.add(chkRegistrar);

        chkMostrar = new JCheckBox("Mostrar eventos en panel");
        chkMostrar.setOpaque(false);
        chkMostrar.setForeground(COLOR_TEXTO_MENU);
        chkMostrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkMostrar.addActionListener(e ->
                mostrarEventosEnPanel = chkMostrar.isSelected());
        panelMenu.add(chkMostrar);

        panelMenu.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        JPanel barraEstado = new JPanel(new BorderLayout());
        barraEstado.add(lblEstado, BorderLayout.CENTER);
        barraEstado.setBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        add(barraEstado, BorderLayout.SOUTH);
    }

    private JButton crearBotonAccion(String texto, Runnable accion) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMargin(new Insets(6, 12, 6, 12));

        btn.addActionListener(e -> accion.run());

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_BOTON_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_BOTON);
            }
        });

        return btn;
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private void accionRegistrarEquipo() {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtEstado = new JTextField();
        JTextField txtValor = new JTextField();
        JTextField txtDesc = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);
        panel.add(new JLabel("Estado:"));
        panel.add(txtEstado);
        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
        panel.add(new JLabel("Desc. técnica:"));
        panel.add(txtDesc);

        int resp = JOptionPane.showConfirmDialog(this, panel,
                "Registrar nuevo equipo", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (resp == JOptionPane.OK_OPTION) {
            try {
                double valor = txtValor.getText().isBlank()
                        ? 0.0
                        : Double.parseDouble(txtValor.getText());
                controlador.registrarEquipo(
                        txtCodigo.getText().trim(),
                        txtNombre.getText().trim(),
                        txtTipo.getText().trim(),
                        txtEstado.getText().trim(),
                        valor,
                        txtDesc.getText().trim()
                );
            } catch (NumberFormatException ex) {
                mostrarError("Valor no válido.");
            }
        }
    }

    private void accionRegistrarMantenimiento() {
        JTextField txtCodigo = new JTextField();
        JTextField txtFecha = new JTextField(LocalDate.now().toString());
        JTextField txtDesc = new JTextField();
        JTextField txtTec = new JTextField();
        JTextField txtCosto = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Código equipo:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Fecha (AAAA-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDesc);
        panel.add(new JLabel("Técnico:"));
        panel.add(txtTec);
        panel.add(new JLabel("Costo:"));
        panel.add(txtCosto);

        int resp = JOptionPane.showConfirmDialog(this, panel,
                "Registrar mantenimiento", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (resp == JOptionPane.OK_OPTION) {
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                double costo = txtCosto.getText().isBlank()
                        ? 0.0
                        : Double.parseDouble(txtCosto.getText());
                controlador.registrarMantenimiento(
                        txtCodigo.getText().trim(),
                        fecha,
                        txtDesc.getText().trim(),
                        txtTec.getText().trim(),
                        costo
                );
            } catch (Exception ex) {
                mostrarError("Datos de mantenimiento no válidos.");
            }
        }
    }

    private void accionVerInventario() {
        EstrategiaOrdenInventario ord = switch (cboOrden.getSelectedIndex()) {
            case 0 -> new OrdenPorTecnico();
            case 1 -> new OrdenPorValor();
            default -> new OrdenPorFechaMantenimiento();
        };

        EstrategiaPresentacionEquipo vista = switch (cboVista.getSelectedIndex()) {
            case 0 -> new VistaCompacta();
            case 1 -> new VistaDetallada();
            default -> new VistaTecnica();
        };

        String texto = controlador.generarReporte(ord, vista);
        areaSalida.setText(texto);
    }

    private void accionGuardarArchivo() {
        try {
            controlador.guardarEnArchivo();
            lblEstado.setText("Inventario guardado en archivo.");
        } catch (IOException e) {
            mostrarError("Error al guardar en archivo: " + e.getMessage());
        }
    }

    private void accionCargarArchivo() {
        try {
            controlador.cargarDesdeArchivo();
            lblEstado.setText("Inventario cargado desde archivo.");
        } catch (IOException e) {
            mostrarError("Error al cargar archivo: " + e.getMessage());
        }
    }

    private void accionGuardarBD() {
        try {
            controlador.guardarEnBD();
            lblEstado.setText("Inventario guardado en BD.");
        } catch (SQLException e) {
            mostrarError("Error al guardar en BD: " + e.getMessage());
        }
    }

    private void accionCargarBD() {
        try {
            controlador.cargarDesdeBD();
            lblEstado.setText("Inventario cargado desde BD.");
        } catch (SQLException e) {
            mostrarError("Error al cargar desde BD: " + e.getMessage());
        }
    }

    private void accionLimpiar() {
        controlador.limpiarInventario();
        areaSalida.setText("");
    }

    private void accionSalir() {
        dispose();
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void manejarCambio(EventoInventario e) {
        String texto = switch (e.getTipo()) {
            case EQUIPO_REGISTRADO ->
                    "Nuevo equipo registrado: " + (e.getEquipo() != null ? e.getEquipo().getCodigo() : "");
            case EQUIPO_ELIMINADO ->
                    "Equipo eliminado";
            case EQUIPO_ACTUALIZADO ->
                    "Equipo actualizado";
            case MANTENIMIENTO_REGISTRADO ->
                    "Mantenimiento para equipo: " + (e.getEquipo() != null ? e.getEquipo().getCodigo() : "");
            case INVENTARIO_CARGADO ->
                    "Inventario cargado (" + e.getDetalle() + ")";
            case INVENTARIO_LIMPIADO ->
                    "Inventario limpiado";
        };

        lblEstado.setText(texto);

        if (mostrarEventosEnPanel) {
            areaSalida.append("[EVENTO] " + texto + "\n");
        }
    }
}
