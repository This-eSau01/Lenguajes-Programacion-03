package hotel;

// Principio de Segregacion de Interfaces (ISP)
// Interfaz especifica para la gestion de personal.
public interface GestionPersonal {
    void asignarPersonalLimpieza(PersonalLimpieza personal, Habitacion habitacion);
}