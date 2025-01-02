package br.com.pedro.order.adapters.in;

import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Validated OrderDTO orderDTO) {
        var order = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderDTO(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> listOrder() {
        var order = orderService.listOrders();
        var response = order.stream().map(OrderDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
