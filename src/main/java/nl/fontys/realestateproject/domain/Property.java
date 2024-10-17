package nl.fontys.realestateproject.domain;

import lombok.*;
import nl.fontys.realestateproject.domain.Enums.ListingType;
import nl.fontys.realestateproject.domain.Enums.PropertyType;

import java.math.BigDecimal;
import java.util.*;

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
}