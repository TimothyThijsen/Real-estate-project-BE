package nl.fontys.realestateproject.business.DTO.Property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String description;
    @NotNull
    private BigDecimal price;
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
