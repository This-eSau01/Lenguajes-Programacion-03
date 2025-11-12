package Actividad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class VentanaFlowLayout extends JFrame {
	private JLabel labelTitulo;
	private JTextField txtNm1;
	private JTextField txtNm2;
	private JLabel labelResultado;
	
	public VentanaFlowLayout() {
		setTitle("Calculador como Ejemplo de Layout");
		setSize(350, 250); //1* Ancho 2*Alto
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		inicializarComponentes();
	}

	private void inicializarComponentes() {
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 10, 15);
		setLayout(layout);
		
		//Creo el titulo de mi programa(Ventana)  
		labelTitulo = new JLabel("Calculadora John");
		labelTitulo.setFont(new Font("Calibri", Font.BOLD, 20));
		labelTitulo.setForeground(Color.BLUE);
		add(labelTitulo);
		
		//Parte para que ingrese Numeros por teclado
		
		JLabel label1 = new JLabel("Number 1:");
		add(label1);
		txtNm1 = new JTextField(10);
		add(txtNm1);
		
		JLabel label2 = new JLabel("Number 2:");
		add(label2);
		txtNm2 = new JTextField(10);
		add(txtNm2);
		
		//Toca crear los botonones principales para mi calculadora las 4 operaciones escenciales
		
		JButton btnSumar = new JButton("Yo sumo");
        btnSumar.setBackground(new Color(46, 204, 113));
        btnSumar.setForeground(Color.WHITE);
        btnSumar.setFocusPainted(false);
        btnSumar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarOperacion("sumar");
            }
        });
        add(btnSumar);
		
        JButton btnRestar = new JButton("Yo Resto");
        btnRestar.setBackground(new Color(52, 152, 219));
        btnRestar.setForeground(Color.WHITE);
        btnRestar.setFocusPainted(false);
        btnRestar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarOperacion("restar");
            }
        });
        add(btnRestar);
		
        JButton btnMultiplicar = new JButton(" Yo Multiplico");
        btnMultiplicar.setBackground(new Color(155, 89, 182));
        btnMultiplicar.setForeground(Color.WHITE);
        btnMultiplicar.setFocusPainted(false);
        btnMultiplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarOperacion("multiplicar");
            }
        });
        add(btnMultiplicar);
        
        JButton btnDividir = new JButton("Yo Divido");
        btnDividir.setBackground(new Color(230, 126, 34));
        btnDividir.setForeground(Color.WHITE);
        btnDividir.setFocusPainted(false);
        btnDividir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarOperacion("dividir");
            }
        });
        add(btnDividir);
        
        
        //Botones escenciales en mi calculadora   
        
        JButton btnLimpiar = new JButton("Yo Limpio");
        btnLimpiar.setBackground(new Color(231, 76, 60));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        add(btnLimpiar);
        
        labelResultado = new JLabel("Resultado:");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        labelResultado.setForeground(new Color(44, 62, 80));
        add(labelResultado);
     
        
       //Mnejo excepciones basicas 
        
	}
	
	private void realizarOperacion(String operacion) {
		String texto1 = txtNm1.getText();
		String texto2 = txtNm2.getText();
		
		 if (texto1.isEmpty() || texto2.isEmpty()) {
             JOptionPane.showMessageDialog(this, 
                 "Por favor ingrese ambos números",
                 "Campos vacíos",
                 JOptionPane.WARNING_MESSAGE);
             return;
         }
         
		 try {
		 
		  double numero1 = Double.parseDouble(texto1);
          double numero2 = Double.parseDouble(texto2);
          double resultado = 0;
          String simbolo = "";
          
          if (operacion.equals("sumar")) {
              resultado = numero1 + numero2;
              simbolo = "+";
          } 
          else if (operacion.equals("restar")) {
              resultado = numero1 - numero2;
              simbolo = "-";
          } 
          else if (operacion.equals("multiplicar")) {
              resultado = numero1 * numero2;
              simbolo = "×";
          } 
          else if (operacion.equals("dividir")) {
              if (numero2 == 0) {
                  JOptionPane.showMessageDialog(this,
                      "No se puede dividir por cero",
                      "Error",
                      JOptionPane.ERROR_MESSAGE);
                  return;
              }
              resultado = numero1 / numero2;
              simbolo = "÷";
          }
         	 
          labelResultado.setText(String.format("Resultado: %.2f %s %.2f = %.2f", 
                  numero1, simbolo, numero2, resultado));

		}
		 
		catch (NumberFormatException ex) {
        
              JOptionPane.showMessageDialog(this,
                  "Por favor ingrese solo números válidos",
                  "Error de formato",
                  JOptionPane.ERROR_MESSAGE);
          }
	}
		
	private void limpiarCampos() {
		txtNm1.setText("");
        txtNm2.setText("");
        labelResultado.setText("Resultado: ---");
        txtNm1.requestFocus();
	}
	
	 public static void main(String[] args) {
	  
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                VentanaFlowLayout ventana = new VentanaFlowLayout();
	                ventana.setVisible(true);
	            }
	        });
	    }
}
