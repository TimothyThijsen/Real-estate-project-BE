package nl.fontys.realestateproject.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.business.dto.property.*;
import nl.fontys.realestateproject.configuration.security.auth.RequestAuthenticatedUserProvider;
import nl.fontys.realestateproject.configuration.security.token.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<GetAllPropertiesByAgentId> getAllPropertiesByAgentId(@PathVariable int agentId) {
        GetAllPropertiesByAgentId response = propertyService.getAllPropertiesByAgentId(agentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<GetAllPropertiesResponse> getAllPropertiesBySearch(@RequestParam(required = false) String searchTerm,
                                                                             @RequestParam(required = false) String listingType,
                                                                             @RequestParam(required = false) BigDecimal minPrice,
                                                                             @RequestParam(required = false) BigDecimal maxPrice,
                                                                             @RequestParam(required = false) Double minSize,
                                                                             @RequestParam(required = false) Integer currentPage,
                                                                             @RequestParam(required = false) Integer pageSize) {
        GetAllPropertiesBySearchRequest request = GetAllPropertiesBySearchRequest.builder()
                .searchTerm(searchTerm)
                .listingType(listingType)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minTotalArea(minSize)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
        GetAllPropertiesResponse response = propertyService.getAllPropertiesBySearch(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/room-size-demand/{agentId}")
 /*   @RolesAllowed({"ADMIN", "AGENT"})*/
    public ResponseEntity<GetRoomSizeDemandResponse> getRoomSizeDemand(@PathVariable Long agentId) {
        /*AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();
        if (!Objects.equals(accessToken.getUserId(), agentId) && !accessToken.getRoles().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }*/
        GetRoomSizeDemandResponse response = propertyService.getRoomSizeDemand(agentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/listingStatus-bySize/{agentId}")
    /*   @RolesAllowed({"ADMIN", "AGENT"})*/
    public ResponseEntity<GetListingStatusByRoomSizeResponse> getListingStatusByRoomSize(@PathVariable Long agentId) {
        /*AccessToken accessToken = requestAuthenticatedUserProvider.getAuthenticatedUserInRequest();
        if (!Objects.equals(accessToken.getUserId(), agentId) && !accessToken.getRoles().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }*/
        GetListingStatusByRoomSizeResponse response = propertyService.getListingStatusByRoomSize(agentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
