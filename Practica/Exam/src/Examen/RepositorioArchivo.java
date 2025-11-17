package Examen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Examen.Modelo.Equipo;
import Examen.Modelo.Mantenimiento;
import Examen.Modelo.ParAsociado;

public class RepositorioArchivo {

    private String rutaArchivo;

    public RepositorioArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @SuppressWarnings("unchecked")
    public List<ParAsociado<Equipo, Mantenimiento>> cargar()
            throws IOException, ClassNotFoundException {

        File f = new File(rutaArchivo);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (List<ParAsociado<Equipo, Mantenimiento>>) obj;
        }
    }

    public void guardar(List<ParAsociado<Equipo, Mantenimiento>> asociaciones)
            throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(new ArrayList<>(asociaciones));
        }
    }
}
