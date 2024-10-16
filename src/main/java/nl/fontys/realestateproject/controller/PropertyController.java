package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.DTO.Property.*;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "${cors.allowedOrigins}")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping()
    public ResponseEntity<CreatePropertyResponse> createProperty(@RequestBody @Valid CreatePropertyRequest request) {
        CreatePropertyResponse response = propertyService.createProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<String> handleInvalidPropertyException(InvalidPropertyException ex) {
        return new ResponseEntity<>(ex.getReason(), HttpStatus.BAD_REQUEST);
    }
    @GetMapping()
    public ResponseEntity<GetAllPropertiesResponse>  getAllProperties() {
        GetAllPropertiesResponse response = propertyService.getAllProperties();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("{propertyId}")
    public ResponseEntity<GetPropertyResponse>  getProperty(@PathVariable int propertyId) {
        GetPropertyResponse response = propertyService.getProperty(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("{propertyId}")
    public ResponseEntity<Void>  deleteProperty(@PathVariable int propertyId) {
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateProperty(@RequestBody @Valid UpdatePropertyRequest request) {
        propertyService.updateProperty(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
