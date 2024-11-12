package nl.fontys.realestateproject.repository;

import nl.fontys.realestateproject.persistence.AddressRepository;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void save_shouldSaveAddressWithAllFields() {
        // Arrange
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        // Act
        AddressEntity savedAddress = addressRepository.save(address);

        // Assert
        assertNotNull(savedAddress);
        assertEquals("Boschdijk", savedAddress.getStreet());
    }

    @Test
    void find_shouldReturnAddress_WhenItExists() {
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        addressRepository.save(address);
        AddressEntity foundAddress = addressRepository.findById(address.getId()).orElse(null);

        assertNotNull(foundAddress);
        assertEquals("Boschdijk", foundAddress.getStreet());
    }
}
