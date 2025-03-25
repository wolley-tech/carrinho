package br.com.wtech.loja.controller;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import br.com.wtech.loja.service.CarrinhoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
public class CarrinhoController {
    private final CarrinhoService service;


    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(@RequestBody CarrinhoDTO carrinhoDTO){
        return service.checkout(carrinhoDTO);
    }
}
