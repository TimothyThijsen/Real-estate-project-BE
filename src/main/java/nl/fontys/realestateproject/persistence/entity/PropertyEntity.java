package nl.fontys.realestateproject.persistence.entity;

import lombok.*;
import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.domain.Property.Enums.ListingType;
import nl.fontys.realestateproject.domain.Property.PropertySurfaceArea;
import nl.fontys.realestateproject.domain.Property.Enums.PropertyType;

import java.util.List;

@Data
@Builder
public class PropertyEntity {
    private Long id;
    private String name;
    private String description;
    private double price;
    private ListingType listingType;
    private PropertyType propertyType;
    private Address address;
    private List<PropertySurfaceArea> surfaceAreas;
}
