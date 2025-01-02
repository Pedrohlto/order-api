package br.com.pedro.order.application.dtos;

import br.com.pedro.order.application.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @JsonProperty("product_identification")
    @NotNull
    @NotEmpty
    private UUID productIdentification;
    private String name;

    @NotNull
    @NotEmpty
    private int quantity;

    @NotNull
    @NotEmpty
    private BigDecimal price;

    public ProductDTO(Product product) {
        this.productIdentification = product.getProductIdentification();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }

    public Product toDomain() {
        return new Product(productIdentification, name, price, quantity);
    }
}
