package hotel;

public class ServicioLimpieza implements GestionPersonal {
    @Override
    public void asignarPersonalLimpieza(PersonalLimpieza personal, Habitacion habitacion) {
        System.out.println("Asignando a " + personal.getNombre() + " para limpiar la habitacion " + habitacion.getNumero());
    }
}
