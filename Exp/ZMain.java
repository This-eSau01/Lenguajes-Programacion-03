package hotel;

import java.time.LocalDate;


public class ZMain {
    public static void main(String[] args) {
        System.out.println("--- SISTEMA DE GESTION DE RESERVAS DE HOTEL ---");

      
        GestionHabitaciones servicioHabitaciones = new ServicioHabitaciones();
        GestionReservas servicioReserva = new ServicioReserva(servicioHabitaciones);


        Cliente cliente1 = new Cliente("John Menacho", "johnmcanales16@gmail.com");
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        
        try {
            System.out.println("\n--- Intentando una reserva exitosa para John Menacho ---");
            Reserva reservaExitosa = servicioReserva.realizarReserva(cliente1, "Individual", hoy, manana);
            if (reservaExitosa != null) {
                System.out.println("Reserva exitosa con ID: " + reservaExitosa.getCliente().getNombre());
                
                System.out.println("\n--- Cancelando la reserva ---");

                PoliticaCancelacion politica = new PoliticaEstandar();
                long diasParaCancelacion = 5;
                double reembolso = politica.calcularMontoAReembolsar(reservaExitosa.getHabitacion().getPrecio(), diasParaCancelacion);
                System.out.println("Monto a reembolsar: $" + reembolso);
                servicioReserva.cancelarReserva(reservaExitosa);
            }
        } catch (ReservaException e) {
            System.err.println("Error de reserva: " + e.getMessage());
        }

        // --- Escenario 2: Reserva con tipo de habitacion inexistente ---
        try {
            System.out.println("\n--- Intentando una reserva fallida por tipo de habitacion ---");
            servicioReserva.realizarReserva(cliente1, "Presidencial", hoy, manana);
        } catch (HabitacionNoEncontrada e) {
            System.err.println("Error de reserva (capturado): " + e.getMessage());
        } catch (ReservaException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        // --- Escenario 3: Reserva con fechas invalidas ---
        try {
            System.out.println("\n--- Intentando una reserva fallida por fechas invalidas ---");
            servicioReserva.realizarReserva(cliente1, "Individual", manana, hoy);
        } catch (ReservaException e) {
            System.err.println("Error de reserva (capturado): " + e.getMessage());
        }
    }
}