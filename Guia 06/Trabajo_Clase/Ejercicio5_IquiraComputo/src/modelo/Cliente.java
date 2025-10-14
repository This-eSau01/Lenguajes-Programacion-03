package modelo;

public class Cliente {
    private String dni;
    private String nombresCompletos;
    private String correoElectronico;
    private String celular;
    private String distritoArequipa;
    private String direccionCompleta;

    public Cliente(String dni, String nombresCompletos, String correoElectronico, 
                   String celular, String distritoArequipa, String direccionCompleta) {
        this.dni = dni;
        this.nombresCompletos = nombresCompletos;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.distritoArequipa = distritoArequipa;
        this.direccionCompleta = direccionCompleta;
    }

    public String getDni() { return dni; }
    public String getNombresCompletos() { return nombresCompletos; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getCelular() { return celular; }
    public String getDistritoArequipa() { return distritoArequipa; }
    public String getDireccionCompleta() { return direccionCompleta; }

    @Override
    public String toString() {
        return nombresCompletos + " - " + dni;
    }
}