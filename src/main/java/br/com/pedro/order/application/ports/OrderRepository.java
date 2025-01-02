package br.com.pedro.order.application.ports;


import br.com.pedro.order.application.domain.Order;

import java.util.List;

public interface OrderRepository {

    public void save(Order order);
    public Order findById(String id);
    public List<Order> findAll();
}
