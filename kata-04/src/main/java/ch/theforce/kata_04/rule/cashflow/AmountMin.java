package ch.theforce.kata_04.rule.cashflow;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Validation rule that ensures the cash flow amount is greater than 100.
 * This rule validates that the amount in a CashFlowDto is strictly greater
 * than 100. If the amount is 100 or less, a validation error is returned.
 */
@Component
public class AmountMin implements ValidationRule<CashFlowDto> {

    /**
     * Validates that the amount in the cash flow is greater than 100.
     *
     * @param target the CashFlowDto to validate
     * @return a Valid result if amount is greater than 100,
     * otherwise an Invalid result with an error message
     */
    @Override
    public ValidationResult<CashFlowDto> validate(CashFlowDto target) {
        return target.getAmount().compareTo(BigDecimal.valueOf(100)) > 0
                ? new Valid<>(target)
                : new Invalid<>(List.of(new
                ValidationError("amount", "Amount must be greater than 100")));
    }
}
