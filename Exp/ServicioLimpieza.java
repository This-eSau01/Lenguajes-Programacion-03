package hotel;

// Principio de Responsabilidad Unica (SRP)
// Solo se encarga de la logica de asignacion de personal.
public class ServicioLimpieza implements GestionPersonal {
    @Override
    public void asignarPersonalLimpieza(PersonalLimpieza personal, Habitacion habitacion) {
        System.out.println("Asignando a " + personal.getNombre() + " para limpiar la habitacion " + habitacion.getNumero());
    }
}