package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.domain.Property.*;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {
    @Mock
    PropertyRepository propertyRepositoryMock;
    @InjectMocks
    PropertyServiceImpl propertyService;

    @Test
    void createProperty_ShouldCreateAProperty() {
        //step 2: setup expectation mock behaviour
        CreatePropertyRequest request = CreatePropertyRequest.builder()
                .name("Lakeside villa")
                .description("Nice lake side villa")
                .street("Street")
                .country("Netherlands")
                .city("Eidnhoven")
                .postalCode("1234AB")
                .propertyType("RESIDENTIAL")
                .listingType("SALE")
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();

        when(propertyRepositoryMock.CreateProperty(any(PropertyEntity.class)))
                .thenReturn(propertyEntity);

        //step 4: call method under test
        CreatePropertyResponse actual = propertyService.createProperty(request);

        //step 5: validate expected mock behaviour
        assertEquals(1L, actual.getPropertyId());
    }


    @Test
    void getAllProperties() {
        PropertyEntity property1 = PropertyEntity.builder().id(1L).name("Property 1").build();
        PropertyEntity property2 = PropertyEntity.builder().id(2L).name("Property 2").build();

        when(propertyRepositoryMock.GetProperties()).thenReturn(List.of(property1,property2));
        GetAllPropertiesResponse actual = propertyService.getAllProperties();
        assertEquals(2, actual.getProperties().size());
        verify(propertyRepositoryMock).GetProperties();
    }

    @Test
    void getProperty_ShouldReturnProperty() {
        PropertyEntity property2 = PropertyEntity.builder().id(2L).name("Property 2").build();

        when(propertyRepositoryMock.GetProperty(2L)).thenReturn(Optional.ofNullable(property2));

        GetPropertyResponse actual = propertyService.getProperty(2L);
        assertEquals("Property 2", actual.getProperty().getName());
        assertEquals(2L, actual.getProperty().getId());
        verify(propertyRepositoryMock).GetProperty(2L);
    }
    @Test
    void getProperty_ShouldThrowException_WhenPropertyNotFound() {
        when(propertyRepositoryMock.GetProperty(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.getProperty(99L));
    }

    @Test
    void updateProperty_ShouldUpdateProperty_WhenRequestIsValid() {
        UpdatePropertyRequest request = UpdatePropertyRequest.builder()
                .id(1L)
                .name("Updated Name")
                .description("Updated Description")
                .street("Updated Street")
                .country("Netherlands")
                .city("Eindhoven")
                .postalCode("1234AB")
                .propertyType("RESIDENTIAL")
                .listingType("SALE")
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();

        when(propertyRepositoryMock.GetProperty(1L)).thenReturn(Optional.of(propertyEntity));
        doNothing().when(propertyRepositoryMock).UpdateProperty(any(PropertyEntity.class));

        propertyService.updateProperty(request);

        verify(propertyRepositoryMock).UpdateProperty(any(PropertyEntity.class));
    }

    @Test
    void updateProperty_ShouldThrowException_WhenPropertyNotFound() {
        UpdatePropertyRequest request = UpdatePropertyRequest.builder()
                .id(99L)
                .name("Updated Name")
                .description("Updated Description")
                .street("Updated Street")
                .country("Netherlands")
                .city("Eindhoven")
                .postalCode("1234AB").build();

        when(propertyRepositoryMock.GetProperty(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.updateProperty(request));
    }

    @Test
    void deleteProperty_ShouldDeleteProperty_WhenIdIsValid() {
        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();
        when(propertyRepositoryMock.GetProperty(1L)).thenReturn(Optional.of(propertyEntity));
        doNothing().when(propertyRepositoryMock).DeleteProperty(1L);

        propertyService.deleteProperty(1);

        verify(propertyRepositoryMock).DeleteProperty(1L);
    }

    @Test
    void deleteProperty_ShouldReturnNotFound_WhenIdIsInvalid() {
        when(propertyRepositoryMock.GetProperty(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.deleteProperty(99));
    }
}