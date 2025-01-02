package br.com.pedro.order.adapters.out.db.documents;

import br.com.pedro.order.application.domain.OrderStatus;
import br.com.pedro.order.application.domain.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
@Builder
public class OrderDocument {

    @Id
    private String id;
    private List<ProductDocument> products;
    private BigDecimal totalValue;
    private BigDecimal discount;
    private final LocalDateTime orderDate;
    private OrderStatus status;

}
