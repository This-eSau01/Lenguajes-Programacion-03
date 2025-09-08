package ActividadClase;

public class Arco implements AtributosArma {
	private int DanioBase;

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
	
	
	
}
