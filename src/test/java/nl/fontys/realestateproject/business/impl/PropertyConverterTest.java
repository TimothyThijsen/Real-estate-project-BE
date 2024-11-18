package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.account.AccountConverter;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyConverterTest {
    @Mock
    AddressConverter addressConverter;
    @Mock
    PropertySurfaceAreaConverter propertySurfaceAreaConverter;
    @Mock
    AccountConverter accountConverter;
    @InjectMocks
    PropertyConverter propertyConverter;

    @Test
    void convert_ShouldReturnProperty() {
        PropertyEntity entity = PropertyEntity.builder()
                .id(1L)
                .description("Nice house")
                .price(BigDecimal.valueOf(100000.0))
                .surfaceAreas(List.of())
                .build();
        Property response = propertyConverter.convert(entity);
        assertEquals(1L, response.getId());
        assertDoesNotThrow(() -> propertyConverter.convert(entity));
    }

    @Test
    void convert_ShouldThrowException_WhenAccountEntityIsNull() {
        PropertyEntity entity = null;
        assertThrows(NullPointerException.class, () -> propertyConverter.convert(entity));
    }
}
