package A1;

public class Coche extends Vehiculo{
	
	@Override
	public void acelerar(int tipo) {
		velocidad += tipo * 2;
	}
}