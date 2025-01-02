package br.com.pedro.order.application.dtos;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonProperty("order_identification")
    private UUID orderIdentification;

    @NotEmpty
    @NotNull
    private List<ProductDTO> products;
    @JsonProperty("total_value")
    private BigDecimal totalValue;
    private BigDecimal discount;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;
    private OrderStatus status;

    public OrderDTO(Order order){
        this.orderIdentification = order.getOrderIdentification();
        this.products = order.getProducts().stream().map(ProductDTO::new).toList();
        this.totalValue = order.getTotalValue();
        this.discount = order.getDiscount();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }

    public Order toDomain(){
        var products = this.products.stream().map(ProductDTO::toDomain).toList();
        return new Order(orderIdentification, products,discount, orderDate);
    }


}
