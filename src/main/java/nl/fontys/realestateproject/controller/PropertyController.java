package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.domain.Property.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping()
    public ResponseEntity<CreatePropertyResponse> createProperty(@RequestBody @Valid CreatePropertyRequest request) {
        CreatePropertyResponse response = propertyService.createProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllPropertiesResponse>  getAllProperties() {
        GetAllPropertiesResponse response = propertyService.getAllProperties();
        return ResponseEntity.ok(response);
    }
    @GetMapping("{propertyId}")
    public ResponseEntity<GetPropertyResponse>  getProperty(@PathVariable int propertyId) {
        GetPropertyResponse response = propertyService.getProperty(propertyId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("{propertyId}")
    public ResponseEntity<DeletePropertyResponse>  deleteProperty(@PathVariable int propertyId) {
        DeletePropertyResponse response = propertyService.deleteProperty(propertyId);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<UpdatePropertyResponse> updateProperty(@RequestBody @Valid UpdatePropertyRequest request) {
        UpdatePropertyResponse response = propertyService.updateProperty(request);
        return ResponseEntity.ok(response);
    }
}
