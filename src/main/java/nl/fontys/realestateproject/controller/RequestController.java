package nl.fontys.realestateproject.controller;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.RequestService;
import nl.fontys.realestateproject.business.dto.request.CreateRequestRequest;
import nl.fontys.realestateproject.business.dto.request.GetActiveRequestsResponse;
import nl.fontys.realestateproject.business.dto.request.GetAllRequestResponse;
import nl.fontys.realestateproject.business.dto.request.UpdateRequestRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class RequestController {
    RequestService requestService;

    @GetMapping("/agent")
    public ResponseEntity<GetAllRequestResponse> getAllByAgentId(@RequestParam long agentId) {
        GetAllRequestResponse response = requestService.getAllByAgentId(agentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/customer")
    public ResponseEntity<GetAllRequestResponse> getAllByCustomerId(@RequestParam long customerId) {
        GetAllRequestResponse response = requestService.getAllByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping()
    public ResponseEntity<Void> createRequest(@RequestBody CreateRequestRequest request) {
        requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateRequest(@RequestBody UpdateRequestRequest request) {
        requestService.updateRequest(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/active")
    public ResponseEntity<GetActiveRequestsResponse> getActiveRequests(@RequestParam long customerId, @RequestParam long propertyId) {
        GetActiveRequestsResponse response =  requestService.getActiveRequests(customerId, propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
