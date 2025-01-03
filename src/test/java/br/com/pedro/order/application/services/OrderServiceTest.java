package br.com.pedro.order.application.services;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.OrderStatus;
import br.com.pedro.order.application.domain.Product;
import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.dtos.ProductDTO;
import br.com.pedro.order.application.exceptions.OrderAlreadyExists;
import br.com.pedro.order.application.exceptions.OrderNotFoundException;
import br.com.pedro.order.application.ports.NotifyExternalSystem;
import br.com.pedro.order.application.ports.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotifyExternalSystem notifyExternalSystem;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new order and salve it on database")
    void createOrder() {

        var orderIdentification = UUID.randomUUID();
        var orderDate = LocalDateTime.now();
        var orderDTO = new OrderDTO(orderIdentification,
                List.of(new ProductDTO(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                orderDate,
                OrderStatus.CREATED);

        var order = orderDTO.toDomain();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        var createdOrder = orderService.createOrder(orderDTO);

        assertNotNull(createdOrder);
        assertEquals(orderIdentification, createdOrder.getOrderIdentification());
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(notifyExternalSystem, times(1)).notify(any(Order.class));
    }

    @Test
    @DisplayName("Should list all orders")
    void listOrders() {
        var order = new Order(UUID.randomUUID(),
                List.of(new Product(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                LocalDateTime.now(),
                OrderStatus.CREATED);

        when(orderRepository.findAll()).thenReturn(List.of(order));

        var orders = orderService.listOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find an order by id")
    void findOrderbyId() {
        var orderId = UUID.randomUUID();
        var order = new Order(orderId,
                List.of(new Product(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                LocalDateTime.now(),
                OrderStatus.CREATED);

        when(orderRepository.findById(orderId.toString())).thenReturn(Optional.of(order));

        var orderDomain = orderService.getOrder(orderId.toString());

        assertNotNull(orderDomain);
        assertEquals(orderId, orderDomain.getOrderIdentification());
        verify(orderRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should validate when Order Already exists")
    void shouldValidateWhenOrderAlreadyExists() {
        var orderId = UUID.randomUUID();
        var order = new Order(orderId,
                List.of(new Product(UUID.randomUUID(), "Product", 1, BigDecimal.TEN)),
                BigDecimal.TEN,
                BigDecimal.ONE,
                LocalDateTime.now(),
                OrderStatus.CREATED);

        when(orderRepository.findById(orderId.toString())).thenReturn(Optional.of(order));
        assertThrows(OrderAlreadyExists.class, () -> orderService.createOrder(new OrderDTO(order)));
    }

    @Test
    @DisplayName("Should validate when order not exists")
    void shouldValidateWhenOrderNotExists() {
        var orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId.toString())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(orderId.toString()));
    }

}