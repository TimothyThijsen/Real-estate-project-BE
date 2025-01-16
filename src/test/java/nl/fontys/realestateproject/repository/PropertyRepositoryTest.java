package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PropertyRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    void save_shouldSavePropertyWithAllFields() {
        // Arrange
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        entityManager.persist(address);
        PropertyEntity property = PropertyEntity.builder()
                .listingType(ListingType.SALE)
                .propertyType(PropertyType.APARTMENT)
                .description("test")
                .price(BigDecimal.valueOf(1000))
                .address(address)
                .build();
        // Act
        PropertyEntity savedProperty = propertyRepository.save(property);

        // Assert
        assertNotNull(savedProperty);
        assertEquals("test", savedProperty.getDescription());
    }

    @Test
    void find_shouldReturnProperty_WhenItExists() {
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        entityManager.persist(address);
        PropertyEntity property = PropertyEntity.builder()
                .listingType(ListingType.SALE)
                .propertyType(PropertyType.APARTMENT)
                .description("test")
                .price(BigDecimal.valueOf(1000))
                .address(address)
                .build();

        entityManager.persist(property);
        entityManager.flush();
        entityManager.clear();
        PropertyEntity savedProperty = entityManager.find(PropertyEntity.class, property.getId());

        assertEquals("test", savedProperty.getDescription());
    }

    @Test
    void findAllByAvailableAndSearchTerm_shouldReturnProperties(){
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        entityManager.persist(address);
        PropertySurfaceAreaEntity propertySurfaceArea = PropertySurfaceAreaEntity.builder().areaInSquareMetre(50.0).nameOfSurfaceArea("House").build();
        List<PropertySurfaceAreaEntity> surfaceAreas = List.of(propertySurfaceArea);

        PropertyEntity property = PropertyEntity.builder()
                .listingType(ListingType.SALE)
                .propertyType(PropertyType.APARTMENT)
                .description("test")
                .price(BigDecimal.valueOf(1000))
                .address(address)
                .surfaceAreas(surfaceAreas)
                .listingStatus("ACTIVE")
                .build();
        propertySurfaceArea.setProperty(property);
        entityManager.persist(property);
        entityManager.persist(propertySurfaceArea);


        entityManager.flush();
        entityManager.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PropertyEntity> properties = propertyRepository.findAllByAvailableAndSearchTerm(
                ListingType.SALE, pageable, BigDecimal.ZERO, BigDecimal.valueOf(1200), "Boschdijk", 10.0);

        assertNotNull(properties);
        assertEquals(1, properties.getTotalElements());
        assertEquals("test", properties.getContent().get(0).getDescription());
    }
    @Test
    void findAllByAvailableAndSearchTerm_shouldReturnNothingSearchTermsDoesntMatch(){
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        entityManager.persist(address);
        PropertySurfaceAreaEntity propertySurfaceArea = PropertySurfaceAreaEntity.builder().areaInSquareMetre(50.0).nameOfSurfaceArea("House").build();
        List<PropertySurfaceAreaEntity> surfaceAreas = List.of(propertySurfaceArea);

        PropertyEntity property = PropertyEntity.builder()
                .listingType(ListingType.SALE)
                .propertyType(PropertyType.APARTMENT)
                .description("test")
                .price(BigDecimal.valueOf(1000))
                .address(address)
                .surfaceAreas(surfaceAreas)
                .listingStatus("ACTIVE")
                .build();
        propertySurfaceArea.setProperty(property);
        entityManager.persist(property);
        entityManager.persist(propertySurfaceArea);


        entityManager.flush();
        entityManager.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PropertyEntity> properties = propertyRepository.findAllByAvailableAndSearchTerm(
                ListingType.SALE, pageable, BigDecimal.valueOf(1200), BigDecimal.ZERO, "Boschdijk", 10.0);

        assertEquals(0, properties.getTotalElements());
    }
}
