package default1;

public enum TipoPlato {
    ENTRADA, FONDO, BEBIDA, POSTRE, OTRO;

    public static TipoPlato fromString(String s) {
        if (s == null) return OTRO;
        switch (s.trim().toUpperCase()) {
            case "ENTRADA": return ENTRADA;
            case "FONDO": return FONDO;
            case "BEBIDA": return BEBIDA;
            case "POSTRE": return POSTRE;
            default: return OTRO;
        }
    }
}
