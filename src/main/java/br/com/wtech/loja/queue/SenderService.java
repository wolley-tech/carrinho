package br.com.wtech.loja.queue;

import br.com.wtech.loja.config.AppConfig;
import br.com.wtech.loja.dto.pagamento.PagamentoRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SenderService {
    private final RabbitTemplate rabbitTemplate;


    public void enviar(PagamentoRequest pagamentoRequest) {
        rabbitTemplate.convertAndSend(AppConfig.topicExchangeName, "loja.pagamento", pagamentoRequest);
    }


}
