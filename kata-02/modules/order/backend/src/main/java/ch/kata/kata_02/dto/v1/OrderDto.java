package ch.kata.kata_02.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long customerId;
    private Long itemId;
    private String itemName;
    private Float price;
    private Integer quantity;
}
