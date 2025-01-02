package br.com.pedro.order.domain;

import br.com.pedro.order.application.domain.exceptions.InvalidProductQuantity;
import br.com.pedro.order.application.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Should create a product with all constructors parameters")
    void shouldCreateProductWithAllConstructorsParameters(){
        var productIdentification = UUID.randomUUID();
        var product = new Product(productIdentification, "Product 1", BigDecimal.valueOf(50.00),1);
        assertNotNull(product);
        assertEquals(productIdentification, product.getProductIdentification());
        assertEquals("Product 1", product.getName());
        assertNotNull(product.getValue());
    }

    @Test
    @DisplayName("Should compare two products by product identification")
    void shouldCompareTwoProductsByProductIdentification(){
        var productIdentification = UUID.randomUUID();
        var product1 = new Product(productIdentification, "Product 1", BigDecimal.valueOf(50.00),2);
        var product2 = new Product(productIdentification, "Product 2", BigDecimal.valueOf(20.00),3);
        assertEquals(product1, product2);
    }

    @Test
    @DisplayName("Should return false when comparing a product with a null object")
    void shouldReturnFalseWhenComparingAProductWithANullObject(){
        var productIdentification = UUID.randomUUID();
        var product = new Product(productIdentification, "Product 1", BigDecimal.valueOf(50.00),1);
        assertNotEquals(null, product);
    }

    @Test
    @DisplayName("Should return false when comparing a product with an object of another class")
    void shouldReturnFalseWhenComparingAProductWithAnObjectOfAnotherClass(){
        var productIdentification = UUID.randomUUID();
        var product = new Product(productIdentification, "Product 1", BigDecimal.valueOf(50.00),1);
        assertNotEquals(new Object(), product);
    }

    @Test
    @DisplayName("Should return the hash code of the product identification")
    void shouldReturnTheHashCodeOfTheProductIdentification(){
        var productIdentification = UUID.randomUUID();
        var product = new Product(productIdentification, "Product 1", BigDecimal.valueOf(50.00),1);
        assertEquals(productIdentification.hashCode(), product.hashCode());
    }

    @Test
    @DisplayName("Should throw an exception when creating a product with a negative quantity")
    void shouldThrowAnExceptionWhenCreatingAProductWithANegativeQuantity(){
        assertThrows(InvalidProductQuantity.class, () -> new Product(UUID.randomUUID(), "Product 1", BigDecimal.valueOf(50.00),-1));
    }

    @Test
    @DisplayName("Should return the total value of the product")
    void shouldReturnTheTotalValueOfTheProduct(){
        var product = new Product(UUID.randomUUID(), "Product 1", BigDecimal.valueOf(50.00),2);
        assertEquals(BigDecimal.valueOf(100.00), product.getTotalValue());
    }
}