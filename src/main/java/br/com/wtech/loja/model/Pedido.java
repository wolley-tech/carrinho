package br.com.wtech.loja.model;

import br.com.wtech.loja.service.PedidoStatusEnum;

import java.util.Objects;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private String cliente;
    private Double total;
    private PedidoStatusEnum status;

    public Pedido() {
    }

    public Pedido(UUID id, String cliente, Double total, PedidoStatusEnum status) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public PedidoStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PedidoStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
