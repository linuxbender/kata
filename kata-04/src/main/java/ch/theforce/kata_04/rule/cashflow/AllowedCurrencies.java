package ch.theforce.kata_04.rule.cashflow;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.validation.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Validation rule that ensures the currency code is one of the allowed currencies.
 * This rule validates that the currency in a CashFlowDto is USD, EUR, or CHF.
 * If the currency is not one of these, a validation error is returned.
 */
@Component
public class AllowedCurrencies implements ValidationRule<CashFlowDto> {

    /**
     * Validates that the currency code is one of the allowed currencies.
     *
     * @param target the CashFlowDto to validate
     * @return a Valid result if currency is USD, EUR, or CHF,
     *         otherwise an Invalid result with an error message
     */
    @Override
    public ValidationResult<CashFlowDto> validate(CashFlowDto target) {

        return target.getCurrencyCd().toUpperCase().matches("USD|EUR|CHF")
                ? new Valid<>(target)
                : new Invalid<>(List.of(new ValidationError("currencyCd", "Currency must be USD, EUR, or CHF")));
    }
}
