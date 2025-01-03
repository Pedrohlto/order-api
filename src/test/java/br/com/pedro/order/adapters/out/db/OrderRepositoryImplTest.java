package br.com.pedro.order.adapters.out.db;

import br.com.pedro.order.adapters.out.db.documents.OrderDocument;
import br.com.pedro.order.adapters.out.db.documents.OrderDocumentRespository;
import br.com.pedro.order.adapters.out.db.documents.ProductDocument;
import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.OrderStatus;
import br.com.pedro.order.application.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderRepositoryImplTest {

    @Mock
    private OrderDocumentRespository orderDocumentRespository;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find all orders successfully")
    void findAllOrdersSuccess() {
        var orderDocument = OrderDocument.builder()
                .id(UUID.randomUUID().toString())
                .products(List.of())
                .totalValue(BigDecimal.TEN)
                .discount(BigDecimal.ONE)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .build();

        when(orderDocumentRespository.findAll()).thenReturn(List.of(orderDocument));

        var orders = orderRepositoryImpl.findAll();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(orderDocumentRespository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should save order successfully")
    void shouldSaveOrderSucessfully(){
       var order = createOrder();
       order.forEach((key, value) -> {
           when(orderDocumentRespository.save(any(OrderDocument.class))).thenReturn(value);
           var orderSaved = orderRepositoryImpl.save(key);
           assertNotNull(orderSaved);
           verify(orderDocumentRespository, times(1)).save(value);
           assertEquals(value.getId(), orderSaved.getOrderIdentification().toString());
           assertEquals(value.getProducts().size(), orderSaved.getProducts().size());
           assertEquals(value.getTotalValue(), orderSaved.getTotalValue());
           assertEquals(value.getDiscount(), orderSaved.getDiscount());
           assertEquals(value.getOrderDate(), orderSaved.getOrderDate());
           assertEquals(value.getStatus(), orderSaved.getStatus());
       });
    }

    @Test
    @DisplayName("Should find order by id successfully")
    void shouldFindOrderByIdSuccessfully(){
        var order = createOrder();
        order.forEach((key, value) -> {
            when(orderDocumentRespository.findById(anyString())).thenReturn(java.util.Optional.of(value));
            var orderFound = orderRepositoryImpl.findById(key.getOrderIdentification().toString()).get();
            assertNotNull(orderFound);
            verify(orderDocumentRespository, times(1)).findById(key.getOrderIdentification().toString());
            assertEquals(value.getId(), orderFound.getOrderIdentification().toString());
            assertEquals(value.getProducts().size(), orderFound.getProducts().size());
            assertEquals(value.getTotalValue(), orderFound.getTotalValue());
            assertEquals(value.getDiscount(), orderFound.getDiscount());
            assertEquals(value.getOrderDate(), orderFound.getOrderDate());
            assertEquals(value.getStatus(), orderFound.getStatus());
        });
    }


    static Map<Order, OrderDocument> createOrder(){
        List<Product> products = new ArrayList<>();
        var product1Id = UUID.randomUUID();
        var product2Id = UUID.randomUUID();
        var product3Id = UUID.randomUUID();
        var orderID    = UUID.randomUUID();
        var orderDate  = LocalDateTime.now();

        products.add(new Product(product1Id,"Product 1", BigDecimal.valueOf(50.00),1));
        products.add(new Product(product2Id,"Product 2", BigDecimal.valueOf(20.00),2));
        products.add(new Product(product3Id,"Product 3", BigDecimal.valueOf(30.00),3));
        Order order = new Order(orderID,products, BigDecimal.ONE, orderDate);
        order.calculateTotalValue();

        List<ProductDocument> productsDocuments = new ArrayList<>();
        productsDocuments.add(new ProductDocument(product1Id.toString(),"Product 1", BigDecimal.valueOf(50.00),1));
        productsDocuments.add(new ProductDocument(product2Id.toString(),"Product 2", BigDecimal.valueOf(20.00),2));
        productsDocuments.add(new ProductDocument(product3Id.toString(),"Product 3", BigDecimal.valueOf(30.00),3));

        var orderDocument = OrderDocument.builder()
                .id(orderID.toString())
                .products(productsDocuments)
                .totalValue(order.getTotalValue())
                .discount(BigDecimal.ONE)
                .orderDate(orderDate)
                .status(OrderStatus.CREATED)
                .build();

        return Map.of(order,orderDocument);
    }

}