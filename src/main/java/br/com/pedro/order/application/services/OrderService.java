package br.com.pedro.order.application.services;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.exceptions.OrderAlreadyExists;
import br.com.pedro.order.application.exceptions.OrderNotFoundException;
import br.com.pedro.order.application.ports.NotifyExternalSystem;
import br.com.pedro.order.application.ports.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotifyExternalSystem notifyExternalSystem;


    public Order createOrder(OrderDTO orderDTO) {
        var order = orderDTO.toDomain();
        validateOrderAlreadyExists(order);
        order.calculateTotalValue();
        orderRepository.save(order);
        notifyExternalSystem.notify(order);
        orderRepository.save(order);
        return order;
    }

    private void validateOrderAlreadyExists(Order order) {
        Optional<Order> byId = orderRepository.findById(order.getOrderIdentification().toString());
        if(byId.isPresent()){
            throw new OrderAlreadyExists("Order Already Exists");
        }
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }
}
