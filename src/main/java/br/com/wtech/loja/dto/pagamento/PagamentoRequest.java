package br.com.wtech.loja.dto.pagamento;

import br.com.wtech.loja.dto.CartaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PagamentoRequest implements Serializable {
    private UUID id;
    private Double total;
    private CartaoDTO cartao;

}
