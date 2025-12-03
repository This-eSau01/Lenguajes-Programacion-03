package Trivilines.servicio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class    ServicioAudioImpl implements ServicioAudio {

    private static final String RUTA_MUSICA_FONDO = "audio/musica_fondo.wav";
    private static final String RUTA_SONIDO_CORRECTO = "audio/sonido_correcto.wav";
    private static final String RUTA_SONIDO_INCORRECTO = "audio/sonido_incorrecto.wav";
    private static final String RUTA_SONIDO_VICTORIA = "audio/sonido_victoria.wav";
    private static final String RUTA_SONIDO_RULETA = "audio/sonido_ruleta.wav";
    private static final String RUTA_SONIDO_COMODIN = "audio/sonido_comodin.wav";
    private static final String RUTA_SONIDO_EXPLOSION = "audio/sonido_explosion.wav";

    private Clip clipMusicaFondo;

    @Override
    public void reproducirMusicaFondo() {
        try {
            detenerMusicaFondo();
            clipMusicaFondo = crearClipDesdeRecurso(RUTA_MUSICA_FONDO);
            if (clipMusicaFondo != null) {
                clipMusicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
                clipMusicaFondo.start();
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir música de fondo: " + e.getMessage());
        }
    }

    @Override
    public void detenerMusicaFondo() {
        if (clipMusicaFondo != null && clipMusicaFondo.isRunning()) {
            clipMusicaFondo.stop();
            clipMusicaFondo.close();
            clipMusicaFondo = null;
        }
    }

    @Override
    public void reproducirSonidoCorrecto() {
        reproducirSonidoSimple(RUTA_SONIDO_CORRECTO);
    }

    @Override
    public void reproducirSonidoIncorrecto() {
        reproducirSonidoSimple(RUTA_SONIDO_INCORRECTO);
    }

    @Override
    public void reproducirSonidoVictoria() {
        reproducirSonidoSimple(RUTA_SONIDO_VICTORIA);
    }

    @Override
    public void reproducirSonidoRuleta() {
        reproducirSonidoSimple(RUTA_SONIDO_RULETA);
    }

    @Override
    public void reproducirSonidoComodin() {
        reproducirSonidoSimple(RUTA_SONIDO_COMODIN);
    }

    @Override
    public void reproducirSonidoExplosion() {
        reproducirSonidoSimple(RUTA_SONIDO_EXPLOSION);
    }

    private void reproducirSonidoSimple(String ruta) {
        new Thread(() -> {
            try {
                Clip clip = crearClipDesdeRecurso(ruta);
                if (clip != null) {
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                    clip.close();
                }
            } catch (Exception e) {
                System.err.println("Error al reproducir sonido " + ruta + ": " + e.getMessage());
            }
        }).start();
    }

    private Clip crearClipDesdeRecurso(String ruta) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url == null) {
            System.err.println("No se encontró el recurso de audio: " + ruta);
            return null;
        }

        try (InputStream in = url.openStream();
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(in)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        }
    }
}
