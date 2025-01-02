package br.com.pedro.order.application.ports;

import br.com.pedro.order.application.domain.Order;

public interface NotifyExternalSystem {
    void notify(Order order);
}
