package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.enums.ListingStatus;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    private long id;
    private String description;
    private BigDecimal price;
    private ListingType listingType;
    private PropertyType propertyType;
    private List<PropertySurfaceArea> surfaceAreas;
    private Address address;
    private Account agent;
    private String imageUrl;
    private ListingStatus listingStatus;
}