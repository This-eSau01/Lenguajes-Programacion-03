package Pryct.repositorio;

import Pryct.modelo.Equipo;
import Pryct.modelo.Mantenimiento;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivo {

    private final String nombreArchivo;

    public RepositorioArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardar(List<Equipo> equipos) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Equipo e : equipos) {
                pw.println("E;" + e.getCodigo() + ";" + e.getNombre() + ";" +
                        e.getTipo() + ";" + e.getEstado() + ";" +
                        e.getValor() + ";" + e.getDescripcionTecnica());
                for (Mantenimiento m : e.getMantenimientos()) {
                    pw.println("M;" + e.getCodigo() + ";" + m.getFecha() + ";" +
                            m.getDescripcion() + ";" + m.getTecnico() + ";" + m.getCosto());
                }
            }
        }
    }

    public List<Equipo> cargar() throws IOException {
        List<Equipo> equipos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes[0].equals("E")) {
                    String cod = partes[1];
                    String nom = partes[2];
                    String tipo = partes[3];
                    String estado = partes[4];
                    double valor = Double.parseDouble(partes[5]);
                    String desc = partes.length > 6 ? partes[6] : "";
                    equipos.add(new Equipo(cod, nom, tipo, estado, valor, desc));
                } else if (partes[0].equals("M")) {
                    String cod = partes[1];
                    Equipo eq = buscarEquipo(equipos, cod);
                    if (eq != null) {
                        LocalDate fecha = LocalDate.parse(partes[2]);
                        String desc = partes[3];
                        String tecnico = partes[4];
                        double costo = Double.parseDouble(partes[5]);
                        eq.getMantenimientos().add(new Mantenimiento(fecha, desc, tecnico, costo));
                    }
                }
            }
        }
        return equipos;
    }

    private Equipo buscarEquipo(List<Equipo> equipos, String codigo) {
        for (Equipo e : equipos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) return e;
        }
        return null;
    }
}
