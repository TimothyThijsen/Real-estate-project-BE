package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressConverterTest {

    @Test
    void convert_ShouldReturnAddress() {
        // Arrange
        AddressConverter addressConverter = new AddressConverter();
        AddressEntity entity = AddressEntity.builder()
                .id(1L)
                .city("Eindhoven")
                .postalCode("5612AB")
                .street("Fontys")
                .country("Netherlands")
                .build();
        Address response = addressConverter.convert(entity);
        assertEquals("Eindhoven", response.getCity());
        assertEquals("5612AB", response.getPostalCode());
        assertEquals("Fontys", response.getStreet());
        assertEquals("Netherlands", response.getCountry());
        assertDoesNotThrow(() -> addressConverter.convert(entity));
    }

    @Test
    void convert_ShouldThrowException_WhenAccountEntityIsNull() {
        AddressConverter addressConverter = new AddressConverter();
        AddressEntity entity = null;
        assertThrows(NullPointerException.class, () -> addressConverter.convert(entity));
    }
}
