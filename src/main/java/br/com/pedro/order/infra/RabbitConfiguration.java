package br.com.pedro.order.infra;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory createConnectionFactory(){
        var connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("localhost:5672");
        connectionFactory.setUsername("myuser");
        connectionFactory.setPassword("mypassword");
        return connectionFactory;
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("order-queue", true);
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order-exchange");
    }

    @Bean
    public Binding binding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order-routing-key");
    }

    @Bean
    public Queue orderQueueDLQ() {
        return new Queue("order-queue-dlq", true);
    }

    @Bean
    public DirectExchange orderExchangeDLQ() {
        return new DirectExchange("order-exchange-dlq");
    }

    @Bean
    public Binding bindingOrderQueueDLQ(Queue orderQueueDLQ, DirectExchange orderExchangeDLQ) {
        return BindingBuilder.bind(orderQueueDLQ).to(orderExchangeDLQ).with("order-routing-key-dlq");
    }

    @Bean
    public Jackson2JsonMessageConverter createMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
