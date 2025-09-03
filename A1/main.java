package A1;

public class main {
	public static void main(String[] args) {
		Vehiculo coche = new Coche();
		Vehiculo bicicleta = new Bicicleta();
		
		coche.acelerar(75);
		bicicleta.acelerar(100);
		
		System.out.println("La velocidad del coche es de: " + coche.getvelocidad());
		System.out.println("La velocidad de la bicicleta es: " + bicicleta.getvelocidad());
	}
}
