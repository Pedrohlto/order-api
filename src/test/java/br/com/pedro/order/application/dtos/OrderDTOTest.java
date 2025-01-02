package br.com.pedro.order.application.dtos;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.OrderStatus;
import br.com.pedro.order.application.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    @DisplayName("Should create a OrderDTO with success")
    void shouldCreateAOrderDTOWithSuccess() {

        var orderIdentification = UUID.randomUUID();
        var orderDate = LocalDateTime.now();

        OrderDTO order = new OrderDTO(orderIdentification,
                List.of(new ProductDTO()),
                BigDecimal.TEN,
                BigDecimal.ONE,
                orderDate,
                OrderStatus.CREATED);

        assertEquals(orderIdentification, order.getOrderIdentification());
        assertEquals(BigDecimal.TEN, order.getTotalValue());
        assertEquals(BigDecimal.ONE, order.getDiscount());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertNotNull(order.getProducts());

    }

    @Test
    @DisplayName("Should create a Order domain with success")
    void shouldCreateAOrderDomainWithSuccess() {

        var orderIdentification = UUID.randomUUID();
        var orderDate = LocalDateTime.now();

        OrderDTO order = new OrderDTO(orderIdentification,
                List.of(new ProductDTO(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                orderDate,
                OrderStatus.CREATED);
        var orderDomain = order.toDomain();
        assertEquals(orderIdentification, orderDomain.getOrderIdentification());
        assertEquals(BigDecimal.ONE, orderDomain.getDiscount());
        assertEquals(orderDate, orderDomain.getOrderDate());
        assertEquals(OrderStatus.CREATED, orderDomain.getStatus());
        assertNotNull(orderDomain.getProducts());
    }

    @Test
    @DisplayName("Should receive a Order domain and convert to OrderDTO")
    void shouldReceiveAOrderDomainAndConvertToOrderDTO() {

        var orderIdentification = UUID.randomUUID();
        var orderDate = LocalDateTime.now();

        Order order = new Order(orderIdentification,
                List.of(new Product(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                orderDate,
                OrderStatus.CREATED);
        var orderDTO = new OrderDTO(order);
        assertEquals(orderIdentification, orderDTO.getOrderIdentification());
        assertEquals(BigDecimal.ONE, orderDTO.getDiscount());
        assertEquals(orderDate, orderDTO.getOrderDate());
        assertEquals(OrderStatus.CREATED, orderDTO.getStatus());
        assertNotNull(orderDTO.getProducts());
    }

}