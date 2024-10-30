package nl.fontys.realestateproject.business.impl;


import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertySurfaceAreaConverterTest {
    @Test
    void convert_ShouldReturnPropertyArea() {
        // Arrange
        PropertySurfaceAreaConverter propertySurfaceAreaConverter = new PropertySurfaceAreaConverter();
        PropertySurfaceAreaEntity entity = PropertySurfaceAreaEntity.builder()
                .id(1L)
                .nameOfSurfaceArea("Room 1")
                .areaInSquareMetre(10.0)
                .build();
        PropertySurfaceArea response = propertySurfaceAreaConverter.convert(entity);
        assertEquals("Room 1", response.getNameOfSurfaceArea());
        assertDoesNotThrow(() -> propertySurfaceAreaConverter.convert(entity));
    }

    @Test
    void convert_ShouldThrowException_WhenAccountEntityIsNull() {
        PropertySurfaceAreaConverter propertySurfaceAreaConverter = new PropertySurfaceAreaConverter();
        PropertySurfaceAreaEntity entity = null;
        assertThrows(NullPointerException.class, () -> propertySurfaceAreaConverter.convert(entity));
    }
}
