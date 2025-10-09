package default1;

import java.util.List;
import java.util.Map;

public class PedidoControlador {
    private final PedidoModelo modelo;
    private final PedidoVista vista;

    public PedidoControlador(PedidoModelo modelo, PedidoVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        String opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case "1":
                    agregarPedido();
                    break;
                case "2":
                    mostrarPedidos();
                    break;
                case "3":
                    eliminarPedido();
                    break;
                case "4":
                    actualizarPedido();
                    break;
                case "5":
                    buscarPedido();
                    break;
                case "6":
                    contarPedidos();
                    break;
                case "7":
                    marcarCompleto();
                    break;
                case "8":
                    mostrarPorEstado();
                    break;
                case "9":
                    contarPendientes();
                    break;
                case "10":
                    verHistorial();
                    break;
                case "11":
                    vista.mostrarMensaje("Saliendo...");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Inténtalo de nuevo.");
            }
        } while (!"11".equals(opcion));
        vista.cerrarScanner();
    }

    private void agregarPedido() {
        String nombre = vista.solicitarNombrePlato();
        TipoPlato tipo = vista.solicitarTipoPlato();
        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarMensaje("El nombre del plato no puede estar vacío.");
            return;
        }
        modelo.agregarPedido(new Pedido(nombre.trim(), tipo));
        vista.mostrarMensaje("Pedido agregado: " + nombre.trim() + " (" + tipo + ")");
    }

    private void mostrarPedidos() {
        vista.mostrarPedidosConIndices(modelo.getPedidos());
    }

    private void eliminarPedido() {
        List<Pedido> lista = modelo.getPedidos();
        if (lista.isEmpty()) {
            vista.mostrarMensaje("No hay pedidos para eliminar.");
            return;
        }
        vista.mostrarPedidosConIndices(lista);
        Integer idx = vista.solicitarIndice();
        if (idx == null) {
            vista.mostrarMensaje("Índice inválido.");
            return;
        }
        boolean ok = modelo.eliminarPorIndice(idx);
        vista.mostrarMensaje(ok ? "Pedido eliminado y registrado en historial." : "Índice fuera de rango.");
    }

    private void actualizarPedido() {
        List<Pedido> lista = modelo.getPedidos();
        if (lista.isEmpty()) {
            vista.mostrarMensaje("No hay pedidos para actualizar.");
            return;
        }
        vista.mostrarPedidosConIndices(lista);
        Integer idx = vista.solicitarIndice();
        if (idx == null) {
            vista.mostrarMensaje("Índice inválido.");
            return;
        }
        Pedido actual = modelo.getPedidoPorIndice(idx);
        if (actual == null) {
            vista.mostrarMensaje("Índice fuera de rango.");
            return;
        }
        String nuevoNombre = vista.solicitarNuevoNombre(actual.getNombrePlato());
        TipoPlato nuevoTipo = vista.solicitarNuevoTipo(actual.getTipo());
        boolean ok = modelo.actualizarPorIndice(idx, nuevoNombre, nuevoTipo);
        vista.mostrarMensaje(ok ? "Pedido actualizado." : "No se pudo actualizar.");
    }

    private void buscarPedido() {
        String modo = vista.solicitarCriterioBusqueda();
        if ("1".equals(modo)) {
            String texto = vista.solicitarTextoBusqueda();
            List<Pedido> res = modelo.buscarPorNombre(texto);
            vista.mostrarPedidos(res);
        } else if ("2".equals(modo)) {
            TipoPlato tipo = vista.solicitarTipoBusqueda();
            List<Pedido> res = modelo.buscarPorTipo(tipo);
            vista.mostrarPedidos(res);
        } else {
            vista.mostrarMensaje("Opción de búsqueda no válida.");
        }
    }

    private void contarPedidos() {
        int total = modelo.contar();
        Map<TipoPlato, Long> porTipo = modelo.contarPorTipo();
        vista.mostrarConteos(porTipo, total);
    }

    private void marcarCompleto() {
        List<Pedido> lista = modelo.getPedidos();
        if (lista.isEmpty()) {
            vista.mostrarMensaje("No hay pedidos para completar.");
            return;
        }
        vista.mostrarPedidosConIndices(lista);
        Integer idx = vista.solicitarIndice();
        if (idx == null) {
            vista.mostrarMensaje("Índice inválido.");
            return;
        }
        boolean ok = modelo.marcarCompletoPorIndice(idx);
        vista.mostrarMensaje(ok ? "Pedido marcado como COMPLETO y registrado en historial." : "Índice fuera de rango.");
    }

    private void mostrarPorEstado() {
        String sel = vista.solicitarEstadoFiltro();
        if ("1".equals(sel)) {
            vista.mostrarPedidos(modelo.getPedidosPorEstado(EstadoPedido.PENDIENTE));
        } else if ("2".equals(sel)) {
            vista.mostrarPedidos(modelo.getPedidosPorEstado(EstadoPedido.COMPLETO));
        } else {
            vista.mostrarMensaje("Selección no válida.");
        }
    }

    private void contarPendientes() {
        long c = modelo.contarPendientes();
        vista.mostrarPendientes(c);
    }

    private void verHistorial() {
        vista.mostrarHistorial(modelo.getHistorial());
    }
}