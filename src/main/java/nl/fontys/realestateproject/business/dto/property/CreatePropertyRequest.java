package nl.fontys.realestateproject.business.dto.property;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePropertyRequest {
    @NotBlank
    @Size(max = 500, message = "Description must be at most 250 characters long")
    private String description;
    @NotNull
    @Max(value = 1000000000, message = "Price must be at most 1000000000")
    private BigDecimal price;
    @NotNull
    private String propertyType;
    @NotNull
    private String listingType;
    @NotEmpty
    private List<PropertySurfaceArea> surfaceAreas;
    @NotBlank
    @Size(max = 50, message = "Street name must be at most 50 characters long")
    private String street;
    @NotBlank
    @Size(max = 50, message = "City name must be at most 50 characters long")
    private String city;
    @NotBlank
    @Size(max = 15, message = "Postal code must be at most 15 characters long")
    private String postalCode;
    @NotBlank
    @Size(max = 50, message = "Country name must be at most 50 characters long")
    private String country;
    @NotNull
    private Long agentId;

    private String imageUrl;

}
