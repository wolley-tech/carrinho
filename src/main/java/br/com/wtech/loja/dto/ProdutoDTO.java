package br.com.wtech.loja.dto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private Long id;
    private String nome;
    private Double preco;

}
