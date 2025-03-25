package br.com.wtech.loja.service;

import br.com.wtech.loja.model.Produto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ProdutoService {
    private static final Set<Produto> produtos = new HashSet<>();


    static {
        produtos.add(new Produto(1L, "Monitor AOC 29'", 550.0));
        produtos.add(new Produto(2L, "Garrafa Termica", 200.0));
        produtos.add(new Produto(3L, "Óculos de sol", 120.0));
        produtos.add(new Produto(4L, "IPhone 16", 12100.0));
        produtos.add(new Produto(5L, "Caderno espiral", 30.0));
    }

    public Set<Produto> listar(){
        return produtos;
    }

    public Optional<Produto> consultar(long id){
       return produtos
                .stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst();
    }

}
