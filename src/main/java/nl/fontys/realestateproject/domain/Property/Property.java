package nl.fontys.realestateproject.domain.Property;

import lombok.*;
import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.domain.ListingType;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.domain.PropertyType;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
 public class Property {
    private long id;
    private String name;
    private String description;
    private double price;
    private ListingType listingType;
    private PropertyType propertyType;
   private List<PropertySurfaceArea> surfaceAreas;
    private Address address;
}