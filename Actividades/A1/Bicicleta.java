package A1;

public class Bicicleta extends Vehiculo {
	
	@Override
	public void acelerar(int tipo) {
		velocidad += tipo;
	}
	
}
