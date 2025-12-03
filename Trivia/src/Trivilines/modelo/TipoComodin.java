package Trivilines.modelo;


public enum TipoComodin {

    CINCUENTA_CINCUENTA("50-50", "Elimina dos respuestas incorrectas"),
    DOBLE_OPORTUNIDAD("Doble oportunidad", "Permite fallar una vez y seguir en la misma pregunta"),
    BOMBA("Bomba", "Reduce el tiempo de respuesta pero duplica la puntuación"),
    TIEMPO_EXTRA("Tiempo extra", "Añade segundos adicionales al temporizador");

    private final String nombreVisible;
    private final String descripcion;

    TipoComodin(String nombreVisible, String descripcion) {
        this.nombreVisible = nombreVisible;
        this.descripcion = descripcion;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
