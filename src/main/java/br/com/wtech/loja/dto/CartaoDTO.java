package br.com.wtech.loja.dto;

import lombok.Data;

@Data
public class CartaoDTO {
    private String titular;
    private String numero;
    private String ccv;

}
