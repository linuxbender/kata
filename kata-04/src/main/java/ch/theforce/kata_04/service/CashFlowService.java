package ch.theforce.kata_04.service;

import ch.theforce.kata_04.dto.CashFlowDto;

/**
 * Service interface for managing cash flow operations.
 * This service provides business logic for handling cash flow data,
 * including validation and persistence of cash flow information.
 */
public interface CashFlowService {

    /**
     * Saves a cash flow transaction.
     * This method persists the provided cash flow data to the database.
     * The cash flow data is validated before being saved.
     *
     * @param cashFlowDto the cash flow data transfer object containing
     *                    the transaction details to be saved
     */
    void saveCashFlow(CashFlowDto cashFlowDto);
}
