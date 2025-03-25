package br.com.wtech.loja.service;

import br.com.wtech.loja.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PedidoService {
    private Set<Pedido> pedidos = new HashSet<>();

    public void criar(String cliente, double total, PedidoStatusEnum status) {
        pedidos.add(new Pedido(UUID.randomUUID(), cliente, total, status));
    }

    public Set<Pedido> listar() {
        return this.pedidos;
    }
}
