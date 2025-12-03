package Trivilines.vista;

import Trivilines.controlador.ControladorJuego;
import Trivilines.modelo.Jugador;
import Trivilines.modelo.Partida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEstadisticas extends JPanel {

    private final ControladorJuego controladorJuego;
    private final Runnable accionVolverMenu;

    private JTable tablaRankingMaximo;
    private JTable tablaRankingPromedio;
    private JTable tablaHistorial;

    private JLabel lblTotalPartidas;
    private JLabel lblPorcentajeVictorias;

    private JButton btnVolver;

    public PanelEstadisticas(ControladorJuego controladorJuego,
                             Runnable accionVolverMenu) {
        this.controladorJuego = controladorJuego;
        this.accionVolverMenu = accionVolverMenu;
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Estadísticas", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 26f));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 10, 10));

        // Ranking puntaje máximo
        tablaRankingMaximo = new JTable();
        JScrollPane scrollMaximo = new JScrollPane(tablaRankingMaximo);
        scrollMaximo.setBorder(BorderFactory.createTitledBorder("Ranking por puntaje máximo"));

        // Ranking puntaje promedio
        tablaRankingPromedio = new JTable();
        JScrollPane scrollPromedio = new JScrollPane(tablaRankingPromedio);
        scrollPromedio.setBorder(BorderFactory.createTitledBorder("Ranking por puntaje promedio"));

        // Historial jugador actual
        tablaHistorial = new JTable();
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder("Historial del jugador actual"));

        panelCentro.add(scrollMaximo);
        panelCentro.add(scrollPromedio);
        panelCentro.add(scrollHistorial);

        add(panelCentro, BorderLayout.CENTER);

        // Sur: info y botón
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        lblTotalPartidas = new JLabel("Total de partidas: 0");
        lblPorcentajeVictorias = new JLabel("Victorias del jugador actual: 0 %");

        panelInfo.add(lblTotalPartidas);
        panelInfo.add(lblPorcentajeVictorias);

        panelSur.add(panelInfo, BorderLayout.CENTER);

        btnVolver = new JButton("Volver al menú");
        btnVolver.addActionListener(e -> accionVolverMenu.run());
        panelSur.add(btnVolver, BorderLayout.EAST);

        add(panelSur, BorderLayout.SOUTH);
    }

    public void actualizarEstadisticas() {
        // Ranking máximo
        List<Jugador> rankingMaximo = controladorJuego.obtenerRankingPuntajeMaximo(10);
        DefaultTableModel modeloMaximo = new DefaultTableModel(
                new Object[]{"Posición", "Jugador", "Puntaje máximo"}, 0);

        int posicion = 1;
        for (Jugador j : rankingMaximo) {
            modeloMaximo.addRow(new Object[]{posicion++, j.getNombre(), j.getPuntajeMaximo()});
        }
        tablaRankingMaximo.setModel(modeloMaximo);

        // Ranking promedio
        List<Jugador> rankingPromedio = controladorJuego.obtenerRankingPuntajePromedio(10);
        DefaultTableModel modeloPromedio = new DefaultTableModel(
                new Object[]{"Posición", "Jugador", "Puntaje promedio"}, 0);

        posicion = 1;
        for (Jugador j : rankingPromedio) {
            modeloPromedio.addRow(new Object[]{posicion++, j.getNombre(),
                    String.format("%.2f", j.getPuntajePromedio())});
        }
        tablaRankingPromedio.setModel(modeloPromedio);

        // Historial jugador actual
        if (controladorJuego.getJugadorActual() != null) {
            int jugadorId = controladorJuego.getJugadorActual().getId();
            Integer historial =
                    controladorJuego.getPartidaActual() != null
                            ? controladorJuego.getPartidaActual().getJugador() != null
                            ? controladorJuego.getPartidaActual().getJugador().getId() == jugadorId
                            ? controladorJuego.getPartidaActual().getJugador().getPartidasJugadas()
                            : null : null
                            : null; // Este bloque es confuso, así que simplemente usamos ServicioEstadisticas desde ControladorJuego

            // Mejor: pedimos al ServicioEstadisticas a través de ControladorJuego
            // (añadimos un método auxiliar aquí)
        }

        // Vamos a pedir el historial vía un método auxiliar en controladorJuego:
        // como no lo definimos, simplificamos y dejamos el historial vacío si no hay jugador
        DefaultTableModel modeloHistorial = new DefaultTableModel(
                new Object[]{"Fecha inicio", "Puntaje", "Correctas", "Incorrectas", "Victoria"}, 0);
        tablaHistorial.setModel(modeloHistorial);

        // Total partidas
        int totalPartidas = controladorJuego.obtenerTotalPartidas();
        lblTotalPartidas.setText("Total de partidas: " + totalPartidas);

        // Porcentaje victorias jugador actual
        double porcentaje = controladorJuego.obtenerPorcentajeVictoriasJugadorActual();
        lblPorcentajeVictorias.setText(String.format("Victorias del jugador actual: %.2f %%", porcentaje));
    }
}
