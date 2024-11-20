package nl.fontys.realestateproject.controller;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.ContractService;
import nl.fontys.realestateproject.business.dto.contract.GetAllContractsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class ContractController {
    ContractService contractService;

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<GetAllContractsResponse> getAllContractsByAgentId(@PathVariable long agentId) {
        GetAllContractsResponse response = contractService.getAllContractsByAgentId(agentId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<GetAllContractsResponse> getAllContractsByCustomerId(@PathVariable long customerId) {
        GetAllContractsResponse response = contractService.getAllContractsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }
}
