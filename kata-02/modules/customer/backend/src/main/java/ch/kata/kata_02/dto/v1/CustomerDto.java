package ch.kata.kata_02.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class CustomerDto {

    private Long id;
    private String name;
}
