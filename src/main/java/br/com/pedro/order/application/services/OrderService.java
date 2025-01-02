package br.com.pedro.order.application.services;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.ports.NotifyExternalSystem;
import br.com.pedro.order.application.ports.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private NotifyExternalSystem notifyExternalSystem;

    public Order createOrder(OrderDTO orderDTO) {
        var order = orderDTO.toDomain();
        orderRepository.save(order);
        System.out.println("Order created: " + order);
        return order;
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
