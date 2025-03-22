package br.com.wtech.loja.model;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Produto produto;
    private Integer quantidade;

}
