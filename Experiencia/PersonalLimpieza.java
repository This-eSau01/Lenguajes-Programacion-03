package hotel;

// Principio de Responsabilidad Unica (SRP)
// Solo se encarga de los datos del personal.
public class PersonalLimpieza {
    private String nombre;
    private String id;

    public PersonalLimpieza(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
}