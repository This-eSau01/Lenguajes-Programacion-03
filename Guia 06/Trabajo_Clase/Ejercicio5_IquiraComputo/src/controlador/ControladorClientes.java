package controlador;

import modelo.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ControladorClientes {
    private List<Cliente> clientesRegistrados;
    private int contadorClientes;

    public ControladorClientes() {
        this.clientesRegistrados = new ArrayList<>();
        this.contadorClientes = 1;
        inicializarClientesDemo();
    }

    private void inicializarClientesDemo() {
        registrarCliente("70123456", "Flavio Arenas Noseque Mas", "Flavio.Arenas@gmail.com", 
                        "951123456", "Cerro Colorado", "Hernando Luque");
        registrarCliente("70876543", "John Menacho Canales", "johnmcanales16@gmail.com", 
                        "952654321", "Cercado", "Calle San Juan 456");
    }

    public boolean registrarCliente(String dni, String nombresCompletos, String correoElectronico,
                                   String celular, String distritoArequipa, String direccionCompleta) {
        if (existeCliente(dni)) {
            return false;
        }

        Cliente nuevoCliente = new Cliente(dni, nombresCompletos, correoElectronico, celular, distritoArequipa, direccionCompleta);
        clientesRegistrados.add(nuevoCliente);
        return true;
    }

    public Cliente buscarClientePorDni(String dni) {
        return clientesRegistrados.stream()
                .filter(cliente -> cliente.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> obtenerTodosClientes() {
        return new ArrayList<>(clientesRegistrados);
    }

    private boolean existeCliente(String dni) {
        return clientesRegistrados.stream()
                .anyMatch(cliente -> cliente.getDni().equals(dni));
    }
}