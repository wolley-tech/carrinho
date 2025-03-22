package br.com.wtech.loja.model;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Carrinho {
    private Long id;
    private Set<Item> items = new HashSet<>();

}
