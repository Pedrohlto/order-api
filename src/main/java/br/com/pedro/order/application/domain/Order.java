package br.com.pedro.order.application.domain;

import br.com.pedro.order.application.domain.exceptions.NoProductsIncluded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {

    private final UUID orderIdentification;
    private List<Product> products;
    private BigDecimal totalValue;
    private BigDecimal discount;
    private final LocalDateTime orderDate;
    private OrderStatus status;

    public Order(UUID orderIdentification, List<Product> products, BigDecimal discount, LocalDateTime orderDate) {
        this.orderIdentification = orderIdentification;
        this.products = validateProductsIncluded(products);
        this.discount = discount;
        this.orderDate = orderDate;
        this.status = OrderStatus.CREATED;
    }

    public Order(LocalDateTime orderDate, List<Product> products) {
        this.orderIdentification = UUID.randomUUID();
        this.products = validateProductsIncluded(products);
        this.orderDate = orderDate;
        this.status = OrderStatus.CREATED;
    }

    public List<Product> validateProductsIncluded(List<Product> products) {
        if (Objects.isNull(products) || products.isEmpty()) {
            throw new NoProductsIncluded();
        }
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public BigDecimal calculateTotalValue() {
        this.totalValue = this.products.stream().map(Product::getTotalValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.totalValue;
    }

    public void orderSent() {
        this.status = OrderStatus.SENT;
    }

}
