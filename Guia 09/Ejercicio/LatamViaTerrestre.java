package Ejercicio;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LatamViaTerrestre extends JFrame implements ActionListener, KeyListener {

    JTextField txtNombre, txtDNI, txtFecha;
    JCheckBox chkAudifonos, chkManta, chkRevista;
    JRadioButton rbPrimerPiso, rbSegundoPiso;
    JComboBox<String> cbOrigen, cbDestino;
    JList<String> listaServicio;
    JButton btnMostrar, btnReiniciar;
    ButtonGroup grupoPisos;
    JLabel lblLogo;

    public LatamViaTerrestre() {
        super("LATAM - Sistema de Compra de Pasajes Terrestres");
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 240, 255));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(25, 25, 112));
        lblLogo = new JLabel(" ");
        lblLogo.setPreferredSize(new Dimension(120, 80));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icono = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image img = icono.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(img));

        panelTitulo.add(lblLogo, BorderLayout.WEST);
        JLabel lblTitulo = new JLabel("LATAM VIAJES TERRESTRES", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.white);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 20));
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        getContentPane().add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(8, 2, 8, 8));
        panelCentral.setBackground(Color.white);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel label = new JLabel("Nombre del pasajero:");
        label.setForeground(new Color(0, 255, 0));
        panelCentral.add(label);
        txtNombre = new JTextField();
        panelCentral.add(txtNombre);

        panelCentral.add(new JLabel("Documento de identidad (DNI):"));
        txtDNI = new JTextField();
        txtDNI.addKeyListener(this);
        panelCentral.add(txtDNI);

        panelCentral.add(new JLabel("Fecha de viaje (dd/mm/aaaa):"));
        txtFecha = new JTextField();
        txtFecha.addKeyListener(this);
        panelCentral.add(txtFecha);

        panelCentral.add(new JLabel("Servicios opcionales:"));
        JPanel panelServicios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelServicios.setBackground(Color.white);
        chkAudifonos = new JCheckBox("Audífonos");
        chkManta = new JCheckBox("Manta");
        chkRevista = new JCheckBox("Revista");
        panelServicios.add(chkAudifonos);
        panelServicios.add(chkManta);
        panelServicios.add(chkRevista);
        panelCentral.add(panelServicios);

        panelCentral.add(new JLabel("Piso del viaje:"));
        JPanel panelPisos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPisos.setBackground(Color.white);
        rbPrimerPiso = new JRadioButton("1° Piso");
        rbSegundoPiso = new JRadioButton("2° Piso");
        grupoPisos = new ButtonGroup();
        grupoPisos.add(rbPrimerPiso);
        grupoPisos.add(rbSegundoPiso);
        panelPisos.add(rbPrimerPiso);
        panelPisos.add(rbSegundoPiso);
        panelCentral.add(panelPisos);

        panelCentral.add(new JLabel("Origen:"));
        cbOrigen = new JComboBox<>(new String[]{"Seleccione...", "Lima", "Arequipa", "Cusco", "Trujillo"});
        panelCentral.add(cbOrigen);

        panelCentral.add(new JLabel("Destino:"));
        cbDestino = new JComboBox<>(new String[]{"Seleccione...", "Puno", "Tacna", "Piura", "Chiclayo"});
        panelCentral.add(cbDestino);

        panelCentral.add(new JLabel("Tipo de servicio:"));
        listaServicio = new JList<>(new String[]{"Económico", "Standard", "VIP"});
        listaServicio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaServicio.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        panelCentral.add(new JScrollPane(listaServicio));

        getContentPane().add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(230, 240, 255));
        btnMostrar = new JButton("Mostrar Resumen");
        btnMostrar.setBackground(new Color(30, 144, 255));
        btnMostrar.setForeground(Color.white);
        btnMostrar.setFocusPainted(false);
        btnMostrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(220, 20, 60));
        btnReiniciar.setForeground(Color.white);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 12));
        btnMostrar.addActionListener(this);
        btnReiniciar.addActionListener(this);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnReiniciar);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del pasajero.");
            return false;
        }
        if (txtDNI.getText().trim().isEmpty() || txtDNI.getText().length() != 8) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI válido de 8 dígitos.");
            return false;
        }
        if (txtFecha.getText().trim().length() != 10) {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha válida en formato dd/mm/aaaa.");
            return false;
        }
        if (!rbPrimerPiso.isSelected() && !rbSegundoPiso.isSelected()) {
            JOptionPane.showMessageDialog(this, "Seleccione el piso del viaje.");
            return false;
        }
        if (cbOrigen.getSelectedIndex() == 0 || cbDestino.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione el origen y destino del viaje.");
            return false;
        }
        if (listaServicio.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de servicio.");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMostrar) {
            if (!validarCampos()) return;

            String nombre = txtNombre.getText();
            String dni = txtDNI.getText();
            String fecha = txtFecha.getText();
            String servicios = "";
            if (chkAudifonos.isSelected()) servicios += "Audífonos ";
            if (chkManta.isSelected()) servicios += "Manta ";
            if (chkRevista.isSelected()) servicios += "Revista ";
            if (servicios.isEmpty()) servicios = "Ninguno";

            String piso = rbPrimerPiso.isSelected() ? "1° Piso" : "2° Piso";
            String origen = (String) cbOrigen.getSelectedItem();
            String destino = (String) cbDestino.getSelectedItem();
            String servicio = listaServicio.getSelectedValue();

            String horaActual = new SimpleDateFormat("HH:mm:ss").format(new Date());

            JOptionPane.showMessageDialog(this,
                    "=== RESUMEN DE COMPRA ===\n\n" +
                            "Nombre: " + nombre +
                            "\nDNI: " + dni +
                            "\nFecha de viaje: " + fecha +
                            "\nHora de registro: " + horaActual +
                            "\nPiso: " + piso +
                            "\nOrigen: " + origen +
                            "\nDestino: " + destino +
                            "\nTipo de Servicio: " + servicio +
                            "\nServicios Adicionales: " + servicios,
                    "LATAM VIAJES TERRESTRES", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == btnReiniciar) {
            txtNombre.setText("");
            txtDNI.setText("");
            txtFecha.setText("");
            grupoPisos.clearSelection();
            chkAudifonos.setSelected(false);
            chkManta.setSelected(false);
            chkRevista.setSelected(false);
            cbOrigen.setSelectedIndex(0);
            cbDestino.setSelectedIndex(0);
            listaServicio.clearSelection();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == txtDNI && !Character.isDigit(e.getKeyChar())) e.consume();
        if (e.getSource() == txtFecha) {
            String texto = txtFecha.getText();
            if (texto.length() == 2 || texto.length() == 5) txtFecha.setText(texto + "/");
            if (texto.length() >= 10) e.consume();
            if (!Character.isDigit(e.getKeyChar())) e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new LatamViaTerrestre();
    }
}
