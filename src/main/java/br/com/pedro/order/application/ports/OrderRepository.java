package br.com.pedro.order.application.ports;


import br.com.pedro.order.application.domain.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order);
    Order findById(String id);
    List<Order> findAll();
}
