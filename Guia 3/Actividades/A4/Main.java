package Act4;



public class Main {
    public static void main(String[] args) {
        // Creamos las armas
        AtributosArma espadaMagica = new Espada(50);
        AtributosArma hachaGuerrera = new Hacha(60); 
        
        // Creamos los personajes
        Jugador spartano = new Jugador("Kratos", 200, 15, hachaGuerrera);
        Enemigo dios = new Enemigo("Zeus", 150, 12, espadaMagica);

        // Iniciamos la pelea
        Combate combate = new Combate();
        combate.iniciarPelea(spartano, dios);
    }
}