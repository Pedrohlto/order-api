package br.com.pedro.order.adapters.in;

import br.com.pedro.order.application.domain.Order;
import br.com.pedro.order.application.domain.Product;
import br.com.pedro.order.application.dtos.OrderDTO;
import br.com.pedro.order.application.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("Should list Orders and return 200 OK")
    void testCreateOrder() throws Exception {

        Map<String, Object> product = new HashMap<>();
        product.put("name", "cellphone");
        product.put("quantity", 10);
        product.put("price", 7000.90);
        product.put("product_identification", "860a1942-3238-472c-9305-7a7a44bf2d36");

        Map<String, Object> payload = new HashMap<>();
        payload.put("products", List.of(product));
        payload.put("discount", null);
        payload.put("order_identification", "59b33ebc-50e7-4921-89ff-2695e9c54e49");
        payload.put("total_value", 70009.00);
        payload.put("order_date", "2024-12-26T20:27:40.737");

        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(montaOrder());

        mockMvc.perform(post("/orders") // Replace "/orders" with the actual endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated()); // Adjust the expected status code accordingly
    }

    Order montaOrder() {
        return new Order(UUID.randomUUID(), List.of(new Product(UUID.randomUUID(), "Product 1", BigDecimal.TEN, 10)), BigDecimal.valueOf(10), LocalDateTime.now());
    }

    @Test
    @DisplayName("Should list Orders and return 200 OK")
    void testListOrders() throws Exception {
       when(orderService.listOrders()).thenReturn(List.of(montaOrder()));
        mockMvc.perform(get("/orders"))
                .andExpect(status().is2xxSuccessful()); // Adjust the expected status code accordingly
    }

    @Test
    @DisplayName("Should find an Order by id and return 200 ok")
    void testFindOrderById() throws Exception {
        var uuidOrder = UUID.randomUUID();
        when(orderService.getOrder(uuidOrder.toString())).thenReturn(montaOrder(uuidOrder));
        mockMvc.perform(get("/orders/"+uuidOrder))
                .andExpect(status().is2xxSuccessful()); // Adjust the expected status code accordingly
    }

    Order montaOrder(UUID uuid) {
        return new Order(uuid, List.of(new Product(UUID.randomUUID(), "Product 1", BigDecimal.TEN, 10)), BigDecimal.valueOf(10), LocalDateTime.now());
    }

}