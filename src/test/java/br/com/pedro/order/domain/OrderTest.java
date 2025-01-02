package br.com.pedro.order.domain;

import br.com.pedro.order.application.domain.exceptions.NoProductsIncluded;
import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.OrderStatus;
import br.com.pedro.order.application.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class OrderTest {

    private static List<Product> VALID_PRODUCT_LIST;

    @BeforeEach
    void createProductListWithThreeProducts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(),"Product 1", BigDecimal.valueOf(50.00),1));
        products.add(new Product(UUID.randomUUID(),"Product 2", BigDecimal.valueOf(20.00),2));
        products.add(new Product(UUID.randomUUID(),"Product 3", BigDecimal.valueOf(30.00),3));
        VALID_PRODUCT_LIST = products;
    }

    @Test
    @DisplayName("Should create an order with all constructors parameters")
    void shouldCreateOrderWithAllConstructorsParameters(){
        UUID orderIdentification = UUID.randomUUID();
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(orderIdentification, VALID_PRODUCT_LIST,  BigDecimal.valueOf(10.00), localDateTimeNow);
        Assertions.assertEquals(orderIdentification, order.getOrderIdentification());
        Assertions.assertEquals(3, order.getProducts().size());
        Assertions.assertEquals(BigDecimal.valueOf(10.00), order.getDiscount());
        Assertions.assertEquals(localDateTimeNow, order.getOrderDate());
    }

    @Test
    @DisplayName("Should create an order with order date and product list")
    void shouldCreateOrderWithOrderDateAndProductList(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        Assertions.assertNotNull(order.getOrderIdentification());
        Assertions.assertEquals(3, order.getProducts().size());
        Assertions.assertEquals(localDateTimeNow, order.getOrderDate());
    }

    @Test
    @DisplayName("Should add a product to an order")
    void shouldAddProductToOrder(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        order.addProduct(new Product(UUID.randomUUID(),"Product 4", BigDecimal.valueOf(40.00),1));
        Assertions.assertEquals(4, order.getProducts().size());
    }

    @Test
    @DisplayName("Should remove a product from an order")
    void shouldRemoveProductFromOrder(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        order.removeProduct(VALID_PRODUCT_LIST.get(0));
        Assertions.assertEquals(2, order.getProducts().size());
    }

    @Test
    @DisplayName("Should list all products from an order")
    void shouldListAllProductsFromOrder(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        List<Product> products = order.getProducts();
        Assertions.assertEquals(3, products.size());
    }

    @Test
    @DisplayName("Should calculate the total value of an order")
    void shouldCalculateTotalValueOfOrder(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        BigDecimal totalValue = order.calculateTotalValue();
        Assertions.assertEquals(BigDecimal.valueOf(180.00), totalValue);
    }

    @Test
    @DisplayName("Should calculate the total value of an order with discount")
    void shouldCalculateTotalValueOfOrderWithDiscount(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(UUID.randomUUID(), VALID_PRODUCT_LIST, BigDecimal.valueOf(10.00), localDateTimeNow);
        BigDecimal totalValue = order.calculateTotalValue();
        Assertions.assertEquals(BigDecimal.valueOf(170.00), totalValue);
    }

    @Test
    @DisplayName("Should validate the discount greater then Zero")
    void shouldValidateTheDiscountGreaterThenZero(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(UUID.randomUUID(), VALID_PRODUCT_LIST, BigDecimal.valueOf(0), localDateTimeNow);
        BigDecimal totalValue = order.calculateTotalValue();
        Assertions.assertEquals(BigDecimal.valueOf(180.00), totalValue);
    }

    @Test
    void testDate(){
        System.out.println(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should throw an exception when try to create an order without products")
    void shouldThrowAnExceptionWhenTryToCreateAnOrderWithoutProducts(){
        var localDateTimeNow = LocalDateTime.now();
        Assertions.assertThrows(NoProductsIncluded.class, () -> new Order(localDateTimeNow, new ArrayList<>()));
    }

    @Test
    @DisplayName("Should throw an exception when try to create an order with null products")
    void shouldThrowAnExceptionWhenTryToCreateAnOrderWithNullProducts(){
        var localDateTimeNow = LocalDateTime.now();
        Assertions.assertThrows(NoProductsIncluded.class, () -> new Order(localDateTimeNow, null));
    }

    @Test
    @DisplayName("Should change orders status")
    void shouldChangeOrderStatus(){
        var localDateTimeNow = LocalDateTime.now();
        Order order = new Order(localDateTimeNow, VALID_PRODUCT_LIST);
        order.orderSent();
        Assertions.assertEquals(OrderStatus.SENT, order.getStatus());
    }

}