package ActividadClase;

public class Hacha implements AtributosArma {
    private int danioBase;

    public Hacha(int danioBase) {
        this.danioBase = danioBase;
    }

    @Override
    public int CalcularDanio(int nivel) {
        return danioBase + (nivel * 5);
    }

    @Override
    public String Implemento() {
        return "escudo";
    }

    @Override
    public String MagiaArma() {
        return "fuego";
    }
}

