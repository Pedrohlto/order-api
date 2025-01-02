package br.com.pedro.order.adapters.in;

import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.services.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "order-queue")
    public void receiveOrder(OrderDTO order){
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("order-exchange-dlq", "order-routing-key-dlq", order);
        }
    }

}
