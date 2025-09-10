package Act4;

public class Arco implements AtributosArma {
	private int DanioBase;
	private int danioElemental = 150;
	private int danioCritico = 15;

	public Arco(int DanioBase) {
		this.DanioBase = DanioBase;
	}
	
	@Override
	public int CalcularDanio(int nivel) {
		return DanioBase + (nivel * 10);
	}
	
	@Override
	public String Implemento() {
		return "Cuerda de Oro";
	}
	
	@Override
	public String MagiaArma() {
		return "Flechas Veneno";
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
