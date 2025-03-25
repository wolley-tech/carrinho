package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import br.com.wtech.loja.dto.pagamento.PagamentoResponse;
import br.com.wtech.loja.model.Pedido;
import br.com.wtech.loja.model.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Service
public class CarrinhoService {
    private final String pagamentoUrl;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;
    private final RestClient restClient;

    public CarrinhoService(@Value("${pagamento.url}") String pagamentoUrl,
                           ProdutoService produtoService,
                           PedidoService pedidoService) {
        this.pagamentoUrl = pagamentoUrl;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.restClient = RestClient.builder()
                .baseUrl(pagamentoUrl)
                .build();
    }

    public ResponseEntity<CheckoutResponseDTO> checkout(CarrinhoDTO carrinhoDTO) {
        //calcula o total
        var total = getTotal(carrinhoDTO);

        //cria um pedido
        var pedido = pedidoService.criar(carrinhoDTO.getCartao().getTitular(),
                total,
                PedidoStatusEnum.PENDENTE);

        var pagamentoRequest = new PagamentoRequest(pedido.getId(), pedido.getTotal(), carrinhoDTO.getCartao());

        CompletableFuture<ResponseEntity<PagamentoResponse>> asyncPagamento = requestAsyncPagamento(pagamentoRequest);

        asyncPagamento.thenAccept(response -> {
            if (response.getStatusCode().is2xxSuccessful()) {
               pedidoService.atualizaStatus(response.getBody().getId(), PedidoStatusEnum.CONFIRMADO);
            } else {
                pedidoService.atualizaStatus(response.getBody().getId(), PedidoStatusEnum.NEGADO);
            }
            System.out.println("Pedido processado...");
        });


        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(new CheckoutResponseDTO("O Pagamento será processado"));
    }


    /**
     * Realiza uma requisição de forma assincrona
     *
     * @param pagamentoRequest
     * @return
     */
    private CompletableFuture<ResponseEntity<PagamentoResponse>> requestAsyncPagamento(PagamentoRequest pagamentoRequest) {
        return CompletableFuture.supplyAsync(() -> restClient.post()
                .uri("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(pagamentoRequest)
                .exchange((request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    PagamentoResponse body = response.bodyTo(PagamentoResponse.class);
                    return ResponseEntity.status(statusCode).body(body);
                }));
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
