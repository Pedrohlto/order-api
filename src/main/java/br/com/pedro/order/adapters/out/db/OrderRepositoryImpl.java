package br.com.pedro.order.adapters.out.db;

import br.com.pedro.order.adapters.out.db.documents.OrderDocument;
import br.com.pedro.order.adapters.out.db.documents.OrderDocumentRespository;
import br.com.pedro.order.adapters.out.db.documents.ProductDocument;
import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.Product;
import br.com.pedro.order.application.ports.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderDocumentRespository orderRepositoryMongo;

    @Override
    public Order save(Order order) {

        OrderDocument orderDocument = OrderDocument.builder()
                .id(order.getOrderIdentification().toString())
                .products(productToProductDocument(order))
                .totalValue(order.getTotalValue())
                .discount(order.getDiscount())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .build();

        orderRepositoryMongo.save(orderDocument);
        return order;
    }

    private static List<ProductDocument> productToProductDocument(Order order) {
        return order.getProducts().stream().map(product -> ProductDocument.builder()
                .id(product.getProductIdentification().toString())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build()).collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(String id) {
        Optional<OrderDocument> byId = orderRepositoryMongo.findById(id);
        return byId.map(this::orderDocumentToOrder);
    }

    @Override
    public List<Order> findAll() {
        var orders = orderRepositoryMongo.findAll();
        return orders.stream().map(this::orderDocumentToOrder).collect(Collectors.toList());
    }

    private Order orderDocumentToOrder(OrderDocument orderDocument) {
        return new Order(UUID.fromString(orderDocument.getId())
                , productDocumentToProduct(orderDocument)
                , orderDocument.getTotalValue()
                , orderDocument.getDiscount()
                , orderDocument.getOrderDate()
                , orderDocument.getStatus());
    }

    private List<Product> productDocumentToProduct(OrderDocument orderDocument) {
        return orderDocument.getProducts().stream().map(productDocument ->
                new Product(UUID.fromString(productDocument.getId())
                        , productDocument.getName()
                        , productDocument.getQuantity()
                        , productDocument.getPrice())).collect(Collectors.toList());
    }
}
