package default1;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PedidoVista {
    private final Scanner scanner;

    public PedidoVista() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\nOpciones:");
        System.out.println("1. Agregar Pedido");
        System.out.println("2. Mostrar Pedidos");
        System.out.println("3. Eliminar Pedido");
        System.out.println("4. Actualizar Pedido");
        System.out.println("5. Buscar Pedido");
        System.out.println("6. Contar Pedidos");
        System.out.println("7. Marcar Pedido como Completo");
        System.out.println("8. Mostrar Pedidos por Estado");
        System.out.println("9. Contador de Pedidos Pendientes");
        System.out.println("10. Ver Historial de Pedidos");
        System.out.println("11. Salir");
    }

    public String solicitarOpcion() {
        System.out.print("Selecciona una opción: ");
        return scanner.nextLine();
    }

    public String solicitarNombrePlato() {
        System.out.print("Introduce el nombre del plato: ");
        return scanner.nextLine();
    }

    public TipoPlato solicitarTipoPlato() {
        System.out.println("Tipo de plato [ENTRADA, FONDO, BEBIDA, POSTRE, OTRO]: ");
        String t = scanner.nextLine();
        return TipoPlato.fromString(t);
    }

    public void mostrarPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos en la lista.");
        } else {
            System.out.println("Lista de Pedidos:");
            for (Pedido pedido : pedidos) {
                System.out.println("- " + pedido);
            }
        }
    }

    public void mostrarPedidosConIndices(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos en la lista.");
        } else {
            System.out.println("Lista de Pedidos (por índice):");
            for (int i = 0; i < pedidos.size(); i++) {
                System.out.println(i + ": " + pedidos.get(i));
            }
        }
    }

    public Integer solicitarIndice() {
        System.out.print("Ingresa el índice del pedido: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return null;
        }
    }

    public String solicitarCriterioBusqueda() {
        System.out.println("Buscar por: 1) Nombre  2) Tipo");
        System.out.print("Elige 1 o 2: ");
        return scanner.nextLine();
    }

    public String solicitarTextoBusqueda() {
        System.out.print("Texto a buscar por nombre: ");
        return scanner.nextLine();
    }

    public TipoPlato solicitarTipoBusqueda() {
        return solicitarTipoPlato();
    }

    public void mostrarConteos(Map<TipoPlato, Long> conteos, int total) {
        System.out.println("Total de pedidos: " + total);
        System.out.println("Por tipo:");
        for (TipoPlato t : TipoPlato.values()) {
            long c = conteos.getOrDefault(t, 0L);
            System.out.println("- " + t + ": " + c);
        }
    }

    public String solicitarEstadoFiltro() {
        System.out.println("Estado: 1) PENDIENTE  2) COMPLETO");
        System.out.print("Elige 1 o 2: ");
        return scanner.nextLine();
    }

    public void mostrarPendientes(long cantidad) {
        System.out.println("Pedidos pendientes: " + cantidad);
    }

    public void mostrarHistorial(List<HistorialRegistro> registros) {
        if (registros.isEmpty()) {
            System.out.println("Historial vacío.");
        } else {
            System.out.println("Historial:");
            for (HistorialRegistro r : registros) {
                System.out.println("- " + r);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String solicitarNuevoNombre(String actual) {
        System.out.print("Nuevo nombre del plato (enter para mantener \"" + actual + "\"): ");
        return scanner.nextLine();
    }

    public TipoPlato solicitarNuevoTipo(TipoPlato actual) {
        System.out.println("Nuevo tipo de plato (enter para mantener \"" + actual + "\")");
        String s = scanner.nextLine();
        if (s == null || s.trim().isEmpty()) return null;
        return TipoPlato.fromString(s);
    }

    public void cerrarScanner() {
        scanner.close();
    }
}