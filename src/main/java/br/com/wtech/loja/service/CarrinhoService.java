package br.com.wtech.loja.service;


import br.com.wtech.loja.dto.CarrinhoDTO;
import br.com.wtech.loja.dto.CheckoutResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    public CheckoutResponseDTO checkout(CarrinhoDTO carrinhoDTO){
        System.out.println(carrinhoDTO);
        return new CheckoutResponseDTO("Pagamento Realizado com sucesso");
    }
}
