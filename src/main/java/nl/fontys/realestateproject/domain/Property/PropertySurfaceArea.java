package nl.fontys.realestateproject.domain.Property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertySurfaceArea {
    private String nameOfSurfaceArea;
    private Double areaInSquareMetre;
}
