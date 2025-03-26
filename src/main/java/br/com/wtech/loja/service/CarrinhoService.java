package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import br.com.wtech.loja.dto.pagamento.PagamentoResponse;
import br.com.wtech.loja.model.Produto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarrinhoService {
    private final ProdutoService produtoService;
    private final PagamentoService pagamentoService;


    public ResponseEntity<CheckoutResponseDTO> checkout(CarrinhoDTO carrinhoDTO) {

        Double total = getTotal(carrinhoDTO);

        PagamentoRequest pagamentoRequest = new PagamentoRequest(total, carrinhoDTO.getCartao());
        ResponseEntity<PagamentoResponse> pagamentoResponse = pagamentoService.processar(pagamentoRequest);


        if (pagamentoResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .ok(new CheckoutResponseDTO("Pagamento Realizado com sucesso"));
        }

        if (pagamentoResponse.getStatusCode().is4xxClientError()) {
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
