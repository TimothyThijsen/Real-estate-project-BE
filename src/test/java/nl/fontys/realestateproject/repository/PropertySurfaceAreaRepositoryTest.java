package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.persistence.PropertySurfaceAreaRepository;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PropertySurfaceAreaRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PropertySurfaceAreaRepository propertySurfaceAreaRepository;

    @Test
    void save_shouldSavePropertySurfaceAreaWithAllFields() {
        // Arrange
        PropertySurfaceAreaEntity propertySurfaceArea = PropertySurfaceAreaEntity.builder()
                .nameOfSurfaceArea("Living room")
                .areaInSquareMetre(15.0)
                .build();

        // Act
        propertySurfaceAreaRepository.save(propertySurfaceArea);
        PropertySurfaceAreaEntity savedPropertySurfaceArea = entityManager.find(PropertySurfaceAreaEntity.class, propertySurfaceArea.getId());

        // Assert
        assertNotNull(savedPropertySurfaceArea);
        assertEquals(15.0, savedPropertySurfaceArea.getAreaInSquareMetre());
    }
    @Test
    void find_shouldReturnPropertySurfaceArea_WhenItExists() {
        PropertySurfaceAreaEntity propertySurfaceArea = PropertySurfaceAreaEntity.builder()
                .nameOfSurfaceArea("Living room")
                .areaInSquareMetre(15.0)
                .build();
        propertySurfaceAreaRepository.save(propertySurfaceArea);
        PropertySurfaceAreaEntity foundPropertySurfaceArea = propertySurfaceAreaRepository.findById(propertySurfaceArea.getId()).orElse(null);

        assertNotNull(foundPropertySurfaceArea);
        assertEquals("Living room", foundPropertySurfaceArea.getNameOfSurfaceArea());
    }
}
