package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

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
}
