package Trivilines.util;

/**
 * Constantes generales de la aplicación.
 */
public final class Constantes {

    private Constantes() {
        // Evita instanciación
    }

    // Ventana principal
    public static final String TITULO_APP = "Juego de Trivia";
    public static final int ANCHO_VENTANA = 1024;
    public static final int ALTO_VENTANA = 768;

    // Rutas de recursos
    public static final String RUTA_DB = "database/trivia.db";

    public static final String RUTA_IMG_FONDO_MENU = "imagenes/fondo_menu.png";
    public static final String RUTA_IMG_FONDO_JUEGO = "imagenes/fondo_juego.png";
    public static final String RUTA_IMG_LOGO = "imagenes/logo.png";
    public static final String RUTA_IMG_RULETA = "imagenes/ruleta.png";

    public static final String RUTA_IMG_COMODIN_5050 = "imagenes/comodines/50-50.png";
    public static final String RUTA_IMG_COMODIN_DOBLE = "imagenes/comodines/doble_oportunidad.png";
    public static final String RUTA_IMG_COMODIN_BOMBA = "imagenes/comodines/bomba.png";
    public static final String RUTA_IMG_COMODIN_TIEMPO = "imagenes/comodines/tiempo_extra.png";

    public static final String RUTA_CAT_CIENCIA = "imagenes/categorias/ciencia.png";
    public static final String RUTA_CAT_ARTE = "imagenes/categorias/arte.png";
    public static final String RUTA_CAT_DEPORTES = "imagenes/categorias/deportes.png";
    public static final String RUTA_CAT_HISTORIA = "imagenes/categorias/historia.png";
    public static final String RUTA_CAT_GEOGRAFIA = "imagenes/categorias/geografia.png";
    public static final String RUTA_CAT_ENTRETENIMIENTO = "imagenes/categorias/entretenimiento.png";

    // Audio
    public static final String AUDIO_MUSICA_FONDO = "audio/musica_fondo.wav";
    public static final String AUDIO_SONIDO_CORRECTO = "audio/sonido_correcto.wav";
    public static final String AUDIO_SONIDO_INCORRECTO = "audio/sonido_incorrecto.wav";
    public static final String AUDIO_SONIDO_VICTORIA = "audio/sonido_victoria.wav";
    public static final String AUDIO_SONIDO_RULETA = "audio/sonido_ruleta.wav";
    public static final String AUDIO_SONIDO_COMODIN = "audio/sonido_comodin.wav";
    public static final String AUDIO_SONIDO_EXPLOSION = "audio/sonido_explosion.wav";

    // Juego
    public static final int PREGUNTAS_POR_PARTIDA = 10;
    public static final int TIEMPO_DEFECTO_PREGUNTA_SEG = 20;

    // Animaciones
    public static final int DURACION_PARPADEO_MS = 800;
    public static final int DURACION_SACUDIDA_MS = 500;
}
