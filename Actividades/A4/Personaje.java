package Act4;

public abstract class Personaje {
    protected String nombre;
    protected int salud;
    protected int nivel;
    protected AtributosArma armaEquipada;

    public Personaje(String nombre, int salud, int nivel, AtributosArma arma) {
        this.nombre = nombre;
        this.salud = salud;
        this.nivel = nivel;
        this.armaEquipada = arma;
    }

    public abstract void atacar(Personaje objetivo);

    public void recibirDanio(int danio) {
        this.salud -= danio;
        System.out.println(this.nombre + " recibe " + danio + " de daño. Salud restante: " + this.salud);
    }

    public boolean estaVivo() {
        return this.salud > 0;
    }

    public String getNombre() {
        return nombre;
    }
}