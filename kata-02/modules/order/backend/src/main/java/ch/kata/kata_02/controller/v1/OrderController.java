package ch.kata.kata_02.controller.v1;

import ch.kata.kata_02.dto.v1.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    public OrderController() {
        log.info("OrderController initialized");
    }

    @GetMapping
    ResponseEntity<List<OrderDto>> getAll() {
        log.info("Retrieving all orders {}", "Hi..");

        var item = new OrderDto(1L, 2L, 3L, "Book", 10.99F, 1);

        return ResponseEntity.ok(List.of(item));
    }

}
