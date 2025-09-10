package ActividadClase;

public class Espada implements AtributosArma{
	private int danioBase;
	
	public Espada(int danioBase) {
		this.danioBase = danioBase;
	}
	
	@Override
	public int CalcularDanio(int nivel) {
		return danioBase + (nivel * 5);
	}
	
	@Override
	public String Implemento() {
		return "Hoja de Agarre";
	}
	
	@Override
	public String MagiaArma() {
		return "Congelar Oponente";
	}
}

