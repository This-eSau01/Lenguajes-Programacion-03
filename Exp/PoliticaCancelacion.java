package hotel;

// Principio Abierto/Cerrado (OCP)
// Permite agregar nuevas politicas de cancelacion.
public interface PoliticaCancelacion {
    double calcularMontoAReembolsar(double precioReserva, long diasRestantes);
}