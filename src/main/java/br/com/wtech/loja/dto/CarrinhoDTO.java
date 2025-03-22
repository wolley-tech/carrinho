package br.com.wtech.loja.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CarrinhoDTO {
    private Long id;
    Set<ItemDTO> itens = new HashSet<>();
    private CartaoDTO cartao;
}
