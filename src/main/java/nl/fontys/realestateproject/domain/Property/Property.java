package nl.fontys.realestateproject.domain.Property;

import lombok.*;
import nl.fontys.realestateproject.domain.Address;
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
    private PropertyType type;
    private Address address;
    private List<PropertySurfaceArea> propertySurfaceAreaList = new ArrayList<>();
}