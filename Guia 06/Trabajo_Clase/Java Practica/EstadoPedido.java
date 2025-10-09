package default1;

public enum EstadoPedido {
    PENDIENTE, COMPLETO, ELIMINADO;

    public static EstadoPedido fromString(String s) {
        if (s == null) return PENDIENTE;
        switch (s.trim().toUpperCase()) {
            case "COMPLETO": return COMPLETO;
            case "ELIMINADO": return ELIMINADO;
            default: return PENDIENTE;
        }
    }
}
