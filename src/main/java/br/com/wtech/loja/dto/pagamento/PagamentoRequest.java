package br.com.wtech.loja.dto.pagamento;

import br.com.wtech.loja.dto.CartaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PagamentoRequest implements Serializable {
    private Double total;
    private CartaoDTO cartao;

}
