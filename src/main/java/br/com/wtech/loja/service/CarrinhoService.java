package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import br.com.wtech.loja.model.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CarrinhoService {
    private final String pagamentoUrl;
    private final ProdutoService produtoService;
    private final RestClient restClient;

    public CarrinhoService(@Value("${pagamento.url}") String pagamentoUrl,
                           ProdutoService produtoService) {
        this.pagamentoUrl = pagamentoUrl;
        this.produtoService = produtoService;
        this.restClient = RestClient.builder()
                .baseUrl(pagamentoUrl)
                .build();
    }

    public ResponseEntity<CheckoutResponseDTO> checkout(CarrinhoDTO carrinhoDTO) {

        Double total = getTotal(carrinhoDTO);

        PagamentoRequest pagamentoRequest = new PagamentoRequest(total, carrinhoDTO.getCartao());

        ResponseEntity<Void> responsePagamento = restClient.post()
                .uri("/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(pagamentoRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    //throws
                }))
                .toBodilessEntity();

        if (responsePagamento.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .ok(new CheckoutResponseDTO("Pagamento Realizado com sucesso"));
        }

        if (responsePagamento.getStatusCode().is4xxClientError()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new CheckoutResponseDTO("Pagamento não autorizado"));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CheckoutResponseDTO("Não foi possível realizar o checkout"));
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
