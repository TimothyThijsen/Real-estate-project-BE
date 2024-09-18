package nl.fontys.realestateproject.domain.Property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;

import java.util.List;

@Data
@Builder
public class UpdatePropertyRequest {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private double price;
    @NotNull
    private String propertyType;
    @NotNull
    private String listingType;
    @NotEmpty
    private List<PropertySurfaceArea> surfaceAreas;
    
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String country;
}
