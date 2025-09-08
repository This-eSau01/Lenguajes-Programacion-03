package ActividadClase;

public class Main {
	public static void main (String[] args) {
		int nivelPersonaje = 10;
		
		System.out.println("--- PRUEBA DE ARMAS ---");
		
	
		AtributosArma hacha = new Hacha(50); 
		System.out.println("\n--- Hacha ---");
		System.out.println("Daño total en nivel " + nivelPersonaje + ": " + hacha.CalcularDanio(nivelPersonaje));
		System.out.println("Implemento: " + hacha.Implemento());
		System.out.println("Magia: " + hacha.MagiaArma());
		
		AtributosArma espada = new Espada(40);
		System.out.println("\n--- Espada ---");
		System.out.println("Daño total en nivel " + nivelPersonaje + ": " + espada.CalcularDanio(nivelPersonaje));
		System.out.println("Implemento: " + espada.Implemento());
		System.out.println("Magia: " + espada.MagiaArma());

		AtributosArma arco = new Arco(30); 
		System.out.println("\n--- Arco ---");
		System.out.println("Daño total en nivel " + nivelPersonaje + ": " + arco.CalcularDanio(nivelPersonaje));
		System.out.println("Implemento: " + arco.Implemento());
		System.out.println("Magia: " + arco.MagiaArma());
	}
}