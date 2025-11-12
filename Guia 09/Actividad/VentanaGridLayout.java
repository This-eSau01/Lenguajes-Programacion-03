package Actividad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaGridLayout extends JFrame {
    
    private JLabel labelInfo;
    private JPanel panelTablero;
    private int clicsTotal = 0;
    
    public VentanaGridLayout() {
        setTitle("Tablero de Colores - GridLayout");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        JLabel titulo = new JLabel("TABLERO DE COLORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(52, 73, 94));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(0, 50));
        add(titulo, BorderLayout.NORTH);
        
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(4, 4, 5, 5));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTablero.setBackground(Color.BLACK);
        
        for (int i = 0; i < 16; i++) {
            JButton boton = new JButton();
            boton.setBackground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cambiarColor(boton);
                }
            });
            panelTablero.add(boton);
        }
        
        add(panelTablero, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        labelInfo = new JLabel("Haz clic en los cuadros para cambiar colores");
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
        panelInferior.add(labelInfo, BorderLayout.NORTH);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(e -> reiniciar());
        panelBotones.add(btnReiniciar);
        
        JButton btnAleatorio = new JButton("Aleatorio");
        btnAleatorio.addActionListener(e -> coloresAleatorios());
        panelBotones.add(btnAleatorio);
        
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cambiarColor(JButton boton) {
        Color[] colores = {
            new Color(231, 76, 60),
            new Color(46, 204, 113),
            new Color(52, 152, 219),
            new Color(241, 196, 15),
            new Color(155, 89, 182),
            new Color(230, 126, 34),
            new Color(26, 188, 156),
            new Color(236, 240, 241)
        };
        
        int indice = (int)(Math.random() * colores.length);
        boton.setBackground(colores[indice]);
        clicsTotal++;
        labelInfo.setText("Clics totales: " + clicsTotal);
    }
    
    private void reiniciar() {
        Component[] componentes = panelTablero.getComponents();
        for (Component comp : componentes) {
            if (comp instanceof JButton) {
                comp.setBackground(Color.WHITE);
            }
        }
        clicsTotal = 0;
        labelInfo.setText("Tablero reiniciado");
    }
    
    private void coloresAleatorios() {
        Component[] componentes = panelTablero.getComponents();
        Color[] colores = {
            new Color(231, 76, 60),
            new Color(46, 204, 113),
            new Color(52, 152, 219),
            new Color(241, 196, 15),
            new Color(155, 89, 182),
            new Color(230, 126, 34),
            new Color(26, 188, 156),
            new Color(149, 165, 166)
        };
        
        for (Component comp : componentes) {
            if (comp instanceof JButton) {
                int indice = (int)(Math.random() * colores.length);
                comp.setBackground(colores[indice]);
            }
        }
        labelInfo.setText("Colores aleatorios aplicados");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaGridLayout ventana = new VentanaGridLayout();
                ventana.setVisible(true);
            }
        });
    }
}
