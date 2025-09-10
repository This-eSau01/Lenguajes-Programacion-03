package Act4;

public class Jugador extends Personaje {
    public Jugador(String nombre, int salud, int nivel, AtributosArma arma) {
        super(nombre, salud, nivel, arma);
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danio = this.armaEquipada.CalcularDanio(this.nivel);
        System.out.println(this.nombre + " ataca con " + this.armaEquipada.Implemento() + " y causa " + danio + " de daño.");
        objetivo.recibirDanio(danio);
    }
}