package hotel;

import java.time.temporal.ChronoUnit;

public class PoliticaEstandar implements PoliticaCancelacion {
    @Override
    public double calcularMontoAReembolsar(double precioReserva, long diasRestantes) {
        if (diasRestantes >= 7) {
            return precioReserva;
        } else if (diasRestantes >= 3) {
            return precioReserva * 0.5;
        } else {
            return 0.0;
        }
    }
}