package default1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PedidoModelo {
    private final List<Pedido> pedidos;
    private final List<HistorialRegistro> historial;

    public PedidoModelo() {
        this.pedidos = new ArrayList<>();
        this.historial = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public boolean eliminarPorIndice(int indice) {
        if (indice < 0 || indice >= pedidos.size()) return false;
        Pedido p = pedidos.get(indice);
        p.setEstado(EstadoPedido.ELIMINADO);
        historial.add(new HistorialRegistro(p.getNombrePlato(), p.getTipo(), p.getEstado(), "ELIMINAR"));
        pedidos.remove(indice);
        return true;
    }

    public boolean actualizarPorIndice(int indice, String nuevoNombre, TipoPlato nuevoTipo) {
        if (indice < 0 || indice >= pedidos.size()) return false;
        Pedido p = pedidos.get(indice);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) p.setNombrePlato(nuevoNombre.trim());
        if (nuevoTipo != null) p.setTipo(nuevoTipo);
        return true;
    }

    public boolean marcarCompletoPorIndice(int indice) {
        if (indice < 0 || indice >= pedidos.size()) return false;
        Pedido p = pedidos.get(indice);
        p.setEstado(EstadoPedido.COMPLETO);
        historial.add(new HistorialRegistro(p.getNombrePlato(), p.getTipo(), p.getEstado(), "COMPLETAR"));
        return true;
    }

    public List<Pedido> buscarPorNombre(String nombre) {
        String n = nombre == null ? "" : nombre.trim().toLowerCase();
        return pedidos.stream().filter(p -> p.getNombrePlato().toLowerCase().contains(n)).collect(Collectors.toList());
    }

    public List<Pedido> buscarPorTipo(TipoPlato tipo) {
        return pedidos.stream().filter(p -> p.getTipo() == tipo).collect(Collectors.toList());
    }

    public List<Pedido> getPedidosPorEstado(EstadoPedido estado) {
        return pedidos.stream().filter(p -> p.getEstado() == estado).collect(Collectors.toList());
    }

    public int contar() {
        return pedidos.size();
    }

    public Map<TipoPlato, Long> contarPorTipo() {
        return pedidos.stream().collect(Collectors.groupingBy(Pedido::getTipo, Collectors.counting()));
    }

    public long contarPendientes() {
        return pedidos.stream().filter(p -> p.getEstado() == EstadoPedido.PENDIENTE).count();
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public Pedido getPedidoPorIndice(int indice) {
        if (indice < 0 || indice >= pedidos.size()) return null;
        return pedidos.get(indice);
    }

    public List<HistorialRegistro> getHistorial() {
        return new ArrayList<>(historial);
    }
}

