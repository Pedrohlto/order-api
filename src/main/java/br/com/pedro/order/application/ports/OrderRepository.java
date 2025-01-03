package br.com.pedro.order.application.ports;


import br.com.pedro.order.application.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findAll();
}
