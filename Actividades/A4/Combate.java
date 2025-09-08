package Act4;


public class Combate {
    public void iniciarPelea(Personaje jugador, Personaje enemigo) {
        System.out.println("\n--- ¡COMIENZA EL COMBATE ENTRE " + jugador.getNombre().toUpperCase() + " Y " + enemigo.getNombre().toUpperCase() + "! ---");
        while (jugador.estaVivo() && enemigo.estaVivo()) {
            jugador.atacar(enemigo);
            if (!enemigo.estaVivo()) {
                System.out.println("\n¡" + jugador.getNombre() + " ha derrotado a " + enemigo.getNombre() + "!");
                break;
            }
            
            enemigo.atacar(jugador);
            if (!jugador.estaVivo()) {
                System.out.println("\n¡" + enemigo.getNombre() + " ha derrotado a " + jugador.getNombre() + "!");
                break;
            }
        }
    }
}