package br.com.wtech.loja.dto.pagamento;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PagamentoResponse {
    private UUID id;
}