package br.com.wtech.loja.service;

import br.com.wtech.loja.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PedidoService {
    private Set<Pedido> pedidos = new HashSet<>();

    public Pedido criar(String cliente, double total, PedidoStatusEnum status) {
        var pedido = new Pedido(UUID.randomUUID(), cliente, total, status);
        pedidos.add(pedido);
        return pedido;
    }

    public void atualizaStatus(UUID pedidoId, PedidoStatusEnum status){
        Pedido pedido = this.pedidos
                .stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Pedido %s não encontrado", pedidoId)));

        pedido.setStatus(status);
        pedidos.add(pedido);

    }

    public Set<Pedido> listar() {
        return this.pedidos;
    }
}
