package Trivilines;

import Trivilines.controlador.ControladorComodines;
import Trivilines.controlador.ControladorJuego;
import Trivilines.database.DatabaseManager;
import Trivilines.repositorio.*;
import Trivilines.servicio.*;
import Trivilines.vista.VentanaPrincipal;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo; // Necesario para buscar el tema Nimbus
import java.awt.*;

public class Principal {

    public static void main(String[] args) {


        try {
            boolean nimbusEncontrado = false;
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusEncontrado = true;
                    break;
                }
            }

            if (!nimbusEncontrado) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            // Si falla, se cargará el diseño por defecto (Metal) y no pasa nada.
            System.err.println("No se pudo aplicar el tema visual: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("--- INICIANDO TRIVILINES ---");

            DatabaseManager.inicializarBaseDeDatos();

            // B) Crear repositorios (Acceso a Datos)
            RepositorioPreguntas repoPreguntas = new RepositorioPreguntasDB();
            RepositorioJugadores repoJugadores = new RepositorioJugadoresImpl();
            RepositorioPartidas repoPartidas = new RepositorioPartidasImpl(repoJugadores);

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

            // E) Crear y mostrar la ventana principal
            VentanaPrincipal ventana = new VentanaPrincipal(
                    controladorJuego,
                    controladorComodines
            );
            ventana.setVisible(true);

            System.out.println("--- VENTANA VISIBLE ---");
        });

    }
}