package br.com.pedro.order.application.domain;

import br.com.pedro.order.application.domain.exceptions.InvalidProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Product {

    private UUID productIdentification;
    private String name;
    private int quantity;
    private BigDecimal price;

    public Product(UUID productIdentification, String name, BigDecimal price, int quantity){
        this.productIdentification = productIdentification;
        this.name = name;
        this.quantity = validateQuantity(quantity);
        this.price = price;
    }

    private int validateQuantity(int quantity){
        if(quantity <= 0){
            throw new InvalidProductQuantity();
        }
        return quantity;
    }

    public BigDecimal getTotalValue(){
        return BigDecimal.valueOf(this.quantity).multiply(this.price);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productIdentification, product.productIdentification);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productIdentification);
    }
}
