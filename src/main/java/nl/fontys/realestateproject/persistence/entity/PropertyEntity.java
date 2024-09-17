package nl.fontys.realestateproject.persistence.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.domain.ListingType;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.domain.PropertyType;

import java.util.ArrayList;
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
