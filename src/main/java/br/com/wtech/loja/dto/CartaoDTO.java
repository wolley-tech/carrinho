package br.com.wtech.loja.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartaoDTO implements Serializable {
    private String titular;
    private String numero;
    private String ccv;

}
