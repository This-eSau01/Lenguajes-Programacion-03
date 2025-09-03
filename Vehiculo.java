package A1;

public abstract class Vehiculo {
	protected int velocidad;
	
	public Vehiculo () {
		this.velocidad = 0;
	}
	
	public int getvelocidad() {
		return velocidad;
	}

	public abstract void acelerar(int tipo);
	
	
}
