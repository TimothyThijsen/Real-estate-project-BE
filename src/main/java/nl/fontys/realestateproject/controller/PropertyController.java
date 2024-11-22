package nl.fontys.realestateproject.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.business.dto.property.*;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;

    @PostMapping()
    @RolesAllowed({"ADMIN", "AGENT"})
    public ResponseEntity<CreatePropertyResponse> createProperty(@RequestBody @Valid CreatePropertyRequest request) {
        CreatePropertyResponse response = propertyService.createProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<String> handleInvalidPropertyException(InvalidPropertyException ex) {
        return new ResponseEntity<>(ex.getReason(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<GetAllPropertiesResponse> getAllProperties() {
        GetAllPropertiesResponse response = propertyService.getAllProperties();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{propertyId}")
    public ResponseEntity<GetPropertyResponse> getProperty(@PathVariable int propertyId) {
        GetPropertyResponse response = propertyService.getProperty(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{propertyId}")
    @RolesAllowed({"ADMIN", "AGENT"})
    public ResponseEntity<Void> deleteProperty(@PathVariable int propertyId) {
        propertyService.deleteProperty(propertyId, requestAuthenticatedUserProvider.getAuthenticatedUserInRequest().getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping()
    @RolesAllowed({"ADMIN", "AGENT"})
    public ResponseEntity<Void> updateProperty(@RequestBody @Valid UpdatePropertyRequest request) {
        AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();
        if (!Objects.equals(accessToken.getUserId(), request.getAgentId()) && !accessToken.getRoles().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        propertyService.updateProperty(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
