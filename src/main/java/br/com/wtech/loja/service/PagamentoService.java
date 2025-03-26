package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import br.com.wtech.loja.dto.pagamento.PagamentoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PagamentoService {
    private static final Set<String> ccvs = new HashSet<>();

    static {
        ccvs.add("321");
        ccvs.add("456");
    }

    public ResponseEntity<PagamentoResponse> processar(PagamentoRequest request) {

        if (ccvs.contains(request.getCartao().getCcv())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new PagamentoResponse(UUID.randomUUID()));
        }

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new PagamentoResponse(UUID.randomUUID()));
    }
}