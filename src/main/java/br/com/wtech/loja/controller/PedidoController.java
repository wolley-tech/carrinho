package br.com.wtech.loja.controller;

import br.com.wtech.loja.model.Pedido;
import br.com.wtech.loja.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    @GetMapping
    public ResponseEntity<Set<Pedido>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}
