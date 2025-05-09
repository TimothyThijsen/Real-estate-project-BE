package nl.fontys.realestateproject.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.TransactionService;
import nl.fontys.realestateproject.business.dto.transaction.GetAllTransactionResponse;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionRequest;
import nl.fontys.realestateproject.business.dto.transaction.MakeTransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    @RolesAllowed({"ADMIN", "CLIENT", "AGENT"})
    public ResponseEntity<MakeTransactionResponse> makeTransaction(@RequestBody @Valid MakeTransactionRequest request) {
        MakeTransactionResponse response = transactionService.makeTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<GetAllTransactionResponse> getAllTransactions() {
        GetAllTransactionResponse response = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/customer/{customerId}")
    @RolesAllowed({"ADMIN", "CLIENT"})
    public ResponseEntity<GetAllTransactionResponse> getAllTransactionsByCustomerId(@PathVariable int customerId) {
        GetAllTransactionResponse response = transactionService.getTransactionsByCustomerId((long) customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/property/{propertyId}")
    @RolesAllowed({"ADMIN", "AGENT"})
    public ResponseEntity<GetAllTransactionResponse> getAllTransactionsByPropertyId(@PathVariable int propertyId) {
        GetAllTransactionResponse response = transactionService.getTransactionsByPropertyId((long) propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
