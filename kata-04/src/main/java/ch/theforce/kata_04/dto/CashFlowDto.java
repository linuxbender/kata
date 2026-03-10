package ch.theforce.kata_04.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object for cash flow information.
 * This DTO encapsulates cash flow details including amount, currency code,
 * and transaction name.
 */
@Getter
@Setter
public class CashFlowDto {
    private BigDecimal amount;
    private String currencyCd;
    private String name;
}
