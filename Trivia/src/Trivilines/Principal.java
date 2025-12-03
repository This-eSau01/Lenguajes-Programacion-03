package Trivilines;

import Trivilines.controlador.ControladorComodines;
import Trivilines.controlador.ControladorJuego;
import Trivilines.database.DatabaseManager;
import Trivilines.repositorio.*;
import Trivilines.servicio.*;
import Trivilines.vista.VentanaPrincipal;

import javax.swing.*;

public class Principal {

    public static void main(String[] args) {
        // Look & Feel del sistema (opcional)
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {
        }

        // Inicializar BD y lanzar interfaz en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            // 1) Inicializar base de datos (crear tablas si no existen)
            DatabaseManager.inicializarBaseDeDatos();

            // 2) Crear repositorios
            RepositorioPreguntas repoPreguntas = new RepositorioPreguntasDB();
            RepositorioJugadores repoJugadores = new RepositorioJugadoresImpl();
            RepositorioPartidas repoPartidas = new RepositorioPartidasImpl(repoJugadores);

            // 3) Crear servicios
            ServicioComodines servicioComodines = new ServicioComodinesImpl();
            ServicioAudio servicioAudio = new ServicioAudioImpl();
            ServicioJuego servicioJuego = new ServicioJuegoImpl(
                    repoPreguntas,
                    repoJugadores,
                    repoPartidas,
                    servicioComodines
            );
            ServicioEstadisticas servicioEstadisticas = new ServicioEstadisticasImpl(
                    repoJugadores,
                    repoPartidas
            );

            // 4) Crear controladores
            ControladorJuego controladorJuego = new ControladorJuego(
                    servicioJuego,
                    servicioAudio,
                    servicioComodines,
                    servicioEstadisticas,
                    repoJugadores
            );
            ControladorComodines controladorComodines = new ControladorComodines(
                    servicioComodines,
                    servicioAudio
            );

            // 5) Crear y mostrar la ventana principal
            VentanaPrincipal ventana = new VentanaPrincipal(
                    controladorJuego,
                    controladorComodines
            );
            ventana.setVisible(true);
        });
    }
}
