package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import br.com.wtech.loja.model.Produto;
import br.com.wtech.loja.queue.SenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarrinhoService {
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;
    private final SenderService senderService;


    public ResponseEntity<CheckoutResponseDTO> checkout(CarrinhoDTO carrinhoDTO) {
        //calcula o total
        var total = getTotal(carrinhoDTO);

        //cria um pedido
        var pedido = pedidoService.criar(carrinhoDTO.getCartao().getTitular(),
                total,
                PedidoStatusEnum.PENDENTE);

        //envia mensagem para fila
        var pagamentoRequest = new PagamentoRequest(pedido.getId(), pedido.getTotal(), carrinhoDTO.getCartao());
        senderService.enviar(pagamentoRequest);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CheckoutResponseDTO("O Pagamento será processado"));
    }


    /**
     * Calcula o total da compra multiplicando o valor do produto pela quantidade.
     *
     * @param carrinhoDTO
     * @return
     */
    private Double getTotal(CarrinhoDTO carrinhoDTO) {
        return carrinhoDTO.getItens()
                .stream()
                .map(itemDTO -> {
                    Produto produto = produtoService.consultar(itemDTO.getProdutoId())
                            .orElseThrow(() ->
                                    new RuntimeException(String.format("Produto %s não encontrado.", itemDTO.getProdutoId())));
                    return produto.getPreco() * itemDTO.getQuantidade();
                })
                .reduce(Double::sum)
                .orElse(0.0D);
    }
}
