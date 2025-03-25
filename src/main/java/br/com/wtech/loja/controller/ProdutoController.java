package br.com.wtech.loja.controller;


import br.com.wtech.loja.model.Produto;
import br.com.wtech.loja.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("produtos")
@AllArgsConstructor
public class ProdutoController {
    private final ProdutoService service;


    @GetMapping
    public ResponseEntity<Set<Produto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

}
