package ch.theforce.kata_04.rule.cashflow;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Validation rule that ensures the cash flow amount is less than 1000.
 * This rule validates that the amount in a CashFlowDto is strictly less
 * than 1000. If the amount is 1000 or greater, a validation error is returned.
 */
@Component
@Slf4j
public class AmountMax implements ValidationRule<CashFlowDto> {

    /**
     * Validates that the amount in the cash flow is less than 1000.
     *
     * @param target the CashFlowDto to validate
     * @return a Valid result if amount is less than 1000,
     *         otherwise an Invalid result with an error message
     */
    @Override
    public ValidationResult<CashFlowDto> validate(CashFlowDto target) {
        log.info("AmountMax");
        return target.getAmount().compareTo(BigDecimal.valueOf(1000)) < 0
                ? new Valid<>(target)
                : new Invalid<>(List.of(new ValidationError("amount", "Amount must be less than 1000")));
    }
}
