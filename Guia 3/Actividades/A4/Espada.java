package Act4;

public class Espada implements AtributosArma{
	private int danioBase;
	private int danioElemental = 15;
	private int danioCritico= 50;
	
	public Espada(int danioBase) {
		this.danioBase = danioBase;
	}
	
	@Override
	public int CalcularDanio(int nivel) {
		return danioBase + (nivel * 5) + this.danioCritico + this.danioElemental;
	}
	
	@Override
	public String Implemento() {
		return "Hoja de Agarre";
	}
	
	@Override
	public String MagiaArma() {
		return "Congelar Oponente";
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

