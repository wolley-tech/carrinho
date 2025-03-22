package br.com.wtech.loja.model;

import lombok.Data;

@Data
public class Produto {
    private Long id;
    private String nome;
    private Double preco;

}
