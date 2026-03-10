package ch.theforce.kata_04.controller;

import ch.theforce.kata_04.dto.CashFlowDto;
import ch.theforce.kata_04.service.CashFlowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST controller for managing cash flow operations.
 * This controller provides HTTP endpoints for retrieving and saving cash flow data.
 * All cash flow operations are delegated to the CashFlowService layer for processing and validation.
 */
@Controller
@AllArgsConstructor
public class CashFlowController {

    private final CashFlowService cashFlowService;

    /**
     * Retrieves cash flow data.
     * This endpoint returns static cash flow information.
     *
     * @return a ResponseEntity with HTTP status OK and cash flow data message
     */
    @GetMapping("/cashflow")
    public ResponseEntity<String> getCashFlow() {
        return ResponseEntity.ok("Cash flow data");
    }

    /**
     * Saves a cash flow transaction.
     * This endpoint accepts cash flow data in the request body, delegates it to the service layer
     * for validation and processing, and returns a confirmation response.
     *
     * @param cashFlowDto the cash flow data transfer object containing transaction details
     * @return a ResponseEntity with HTTP status OK and success message
     */
    @PostMapping("/cashflow")
    public ResponseEntity<String> postCashFlow(@RequestBody CashFlowDto cashFlowDto) {
        cashFlowService.saveCashFlow(cashFlowDto);
        return ResponseEntity.ok("Cash flow saved");
    }
}


