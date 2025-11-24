package Pryct.notificaciones;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ObservadorLogArchivo implements ObservadorInventario {

    private final String nombreArchivo;

    public ObservadorLogArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void manejarCambio(EventoInventario e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, true))) {
            pw.println(e.getTipo() + " | " + e.getOrigen() + " | " + e.getDetalle());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
