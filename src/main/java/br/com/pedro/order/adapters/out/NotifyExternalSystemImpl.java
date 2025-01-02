package br.com.pedro.order.adapters.out;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.ports.NotifyExternalSystem;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;

@Component
public class NotifyExternalSystemImpl implements NotifyExternalSystem {

    @Override
    @CircuitBreaker(name="notify", fallbackMethod="fallback")
    public Order notify(Order order) {
        System.out.println("Notificando sistema externo sobre a ordem: " + order + " Possivel chamada sincrona");
        order.orderSent();
        if(1 == 1) {
            throw new RuntimeException("Teste CircuitBreaker");
        }
        return order;
    }

    public Order fallback(Order order, Throwable throwable) {
        System.out.println("Resposta alternativa devido a falha: " + throwable.getMessage());
        order.orderSent();
        return order;
    }
}
