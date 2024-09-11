package nl.fontys.realestateproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.CreatePropertyUseCase;
import nl.fontys.realestateproject.business.GetPropertiesUseCase;
import nl.fontys.realestateproject.domain.CreatePropertyRequest;
import nl.fontys.realestateproject.domain.CreatePropertyResponse;
import nl.fontys.realestateproject.domain.GetAllPropertiesResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final CreatePropertyUseCase createPropertyUseCase;
    private final GetPropertiesUseCase getPropertiesUseCase;

    @PostMapping()
    public ResponseEntity<CreatePropertyResponse> createStudent(@RequestBody @Valid CreatePropertyRequest request) {
        CreatePropertyResponse response = createPropertyUseCase.CreateProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetAllPropertiesResponse>  getAllProperties() {
        GetAllPropertiesResponse response = getPropertiesUseCase.getAllProperties();
        return ResponseEntity.ok(response);
    }
}
