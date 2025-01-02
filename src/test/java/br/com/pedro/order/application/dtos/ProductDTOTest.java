package br.com.pedro.order.application.dtos;

import br.com.pedro.order.application.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDTOTest {

    @Test
    @DisplayName("Should create a ProductDTO with sucess")
    void shoulCreateProductDTOWithSucess(){
        ProductDTO productDTO = new ProductDTO(UUID.randomUUID(), "Product 1", 1, BigDecimal.valueOf(10.0));
        assertEquals("Product 1",productDTO.getName());
        assertEquals(1, productDTO.getQuantity());
        assertEquals(BigDecimal.valueOf(10.0), productDTO.getPrice());
    }

    @Test
    @DisplayName("Should create a Product Domain by ProductDTO")
    void shouldCreateProductDomainByProductDTO(){
        ProductDTO productDTO = new ProductDTO(UUID.randomUUID(), "Product 1", 1, BigDecimal.valueOf(10.0));
        Product product = productDTO.toDomain();
        assertEquals("Product 1",product.getName());
        assertEquals(1, product.getQuantity());
        assertEquals(BigDecimal.valueOf(10.0), product.getPrice());
    }

    @Test
    @DisplayName("Should receive a Product Domain and return a ProductDTO")
    void shouldReceiveProductDomainAndReturnProductDTO(){
        Product product = new Product(UUID.randomUUID(), "Product 1", 1, BigDecimal.valueOf(10.0));
        ProductDTO productDTO = new ProductDTO(product);
        assertEquals("Product 1",productDTO.getName());
        assertEquals(1, productDTO.getQuantity());
        assertEquals(BigDecimal.valueOf(10.0), productDTO.getPrice());
    }

}