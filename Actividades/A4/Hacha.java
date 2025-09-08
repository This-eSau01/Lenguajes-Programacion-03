package Act4;

public class Hacha implements AtributosArma {
    private int danioBase;
    private int danioElemental = 500;
    private int danioCritico = 20;

    public Hacha(int danioBase) {
        this.danioBase = danioBase;
    }

    @Override
    public int CalcularDanio(int nivel) {
        return danioBase + (nivel * 5) + this.danioCritico + this.danioElemental;
    }

    @Override
    public String Implemento() {
        return "escudo";
    }

    @Override
    public String MagiaArma() {
        return "fuego";
    }

	@Override
	public int getDanioElemental() {
		return this.danioElemental;
	}

	@Override
	public int getDanioCritico() {
		return this.danioCritico;
	}
}

