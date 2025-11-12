package Actividad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaBorderLayout extends JFrame {
    
    private JTextArea areaTexto;
    private JLabel labelEstado;
    
    public VentanaBorderLayout() {
        setTitle("Visor de Mensajes - BorderLayout");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        JLabel titulo = new JLabel("VISOR DE MENSAJES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(52, 152, 219));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(0, 50));
        add(titulo, BorderLayout.NORTH);
        
        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        areaTexto.setLineWrap(true);
        areaTexto.setEditable(false);
        areaTexto.setText("Presiona los botones para ver mensajes aquí...");
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);
        
        labelEstado = new JLabel("Estado: Listo");
        labelEstado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(labelEstado, BorderLayout.SOUTH);
        
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(4, 1, 5, 5));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelIzquierdo.setPreferredSize(new Dimension(150, 0));
        
        JButton btnSaludo = new JButton("Saludo");
        btnSaludo.addActionListener(e -> mostrarMensaje("Buenas, El GTA6 NO SALDRA"));
        
        JButton btnInfo = new JButton("Info");
        btnInfo.addActionListener(e -> mostrarMensaje("Info.rockstrickis y via DaniRep "));
        
        JButton btnAlerta = new JButton("Alerta");
        btnAlerta.addActionListener(e -> mostrarMensaje("¡ATENCIÓN! GTA7 CONFIRMADO"));
        
        JButton btnLimpiar = new JButton(" Limpiar");
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelIzquierdo.add(btnSaludo);
        panelIzquierdo.add(btnInfo);
        panelIzquierdo.add(btnAlerta);
        panelIzquierdo.add(btnLimpiar);
        add(panelIzquierdo, BorderLayout.WEST);
        
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(new Color(236, 240, 241));
        panelDerecho.setPreferredSize(new Dimension(120, 0));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelInfo = new JLabel("<html><b>Regiones:</b><br><br>" +
                                      "• NORTH<br>" +
                                      "• CENTER<br>" +
                                      "• SOUTH<br>" +
                                      "• WEST<br>" +
                                      "• EAST</html>");
        panelDerecho.add(labelInfo);
        add(panelDerecho, BorderLayout.EAST);
    }
    
    private void mostrarMensaje(String mensaje) {
        areaTexto.setText(mensaje);
        labelEstado.setText("Estado: Mensaje mostrado");
    }
    
    private void limpiar() {
        areaTexto.setText("");
        labelEstado.setText("Estado: Limpiado");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaBorderLayout ventana = new VentanaBorderLayout();
            ventana.setVisible(true);
        });
    }
}
