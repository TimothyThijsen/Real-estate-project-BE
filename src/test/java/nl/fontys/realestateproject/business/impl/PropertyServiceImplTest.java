package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.DTO.Property.*;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.persistence.AddressRepository;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.PropertySurfaceAreaRepository;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
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
    @Mock
    AddressRepository addressRepositoryMock;
    @Mock
    PropertySurfaceAreaRepository propertySurfaceAreaRepositoryMock;
    @InjectMocks
    PropertyServiceImpl propertyService;

    @Test
    void createProperty_ShouldCreateAProperty() {
        //step 2: setup expectation mock behaviour
        CreatePropertyRequest request = CreatePropertyRequest.builder()
                .description("Nice lake side villa")
                .street("Street")
                .country("Netherlands")
                .city("Eidnhoven")
                .postalCode("1234AB")
                .propertyType("RESIDENTIAL")
                .listingType("SALE")
                .surfaceAreas(List.of())
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();
        when(propertyRepositoryMock.save(any(PropertyEntity.class)))
                .thenReturn(propertyEntity);

        //step 4: call method under test
        CreatePropertyResponse actual = propertyService.createProperty(request);

        //step 5: validate expected mock behaviour
        assertEquals(1L, actual.getPropertyId());
    }


    @Test
    void getAllProperties() {
        PropertyEntity property1 = PropertyEntity.builder().id(1L).description("Property 1").address(AddressEntity.builder().street("street").build()).surfaceAreas(List.of()).build();
        PropertyEntity property2 = PropertyEntity.builder().id(2L).description("Property 2").address(AddressEntity.builder().street("street").build()).surfaceAreas(List.of()).build();

        when(propertyRepositoryMock.findAll()).thenReturn(List.of(property1,property2));
        GetAllPropertiesResponse actual = propertyService.getAllProperties();

        assertEquals(2, actual.getProperties().size());
        verify(propertyRepositoryMock).findAll();
    }

    @Test
    void getProperty_ShouldReturnProperty() {
        PropertyEntity property2 = PropertyEntity.builder().id(2L).description("Property 2").address(AddressEntity.builder().street("street").build()).surfaceAreas(List.of()).build();

        when(propertyRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(property2));

        GetPropertyResponse actual = propertyService.getProperty(2L);

        assertEquals("Property 2", actual.getProperty().getDescription());
        assertEquals(2L, actual.getProperty().getId());
        verify(propertyRepositoryMock).findById(2L);
    }
    @Test
    void getProperty_ShouldThrowException_WhenPropertyNotFound() {
        when(propertyRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.getProperty(99L));
    }

    @Test
    void updateProperty_ShouldUpdateProperty_WhenRequestIsValid() {
        UpdatePropertyRequest request = UpdatePropertyRequest.builder()
                .id(1L)
                .description("Updated Description")
                .street("Updated Street")
                .country("Netherlands")
                .city("Eindhoven")
                .postalCode("1234AB")
                .propertyType("RESIDENTIAL")
                .listingType("SALE")
                .surfaceAreas(List.of())
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();

        when(propertyRepositoryMock.existsById(1L)).thenReturn(true);

        propertyService.updateProperty(request);

        verify(propertyRepositoryMock).save(any(PropertyEntity.class));
    }

    @Test
    void updateProperty_ShouldThrowException_WhenPropertyNotFound() {
        UpdatePropertyRequest request = UpdatePropertyRequest.builder()
                .id(99L)
                .description("Updated Description")
                .street("Updated Street")
                .country("Netherlands")
                .city("Eindhoven")
                .postalCode("1234AB").build();



        assertThrows(InvalidPropertyException.class, () -> propertyService.updateProperty(request));
    }

    @Test
    void deleteProperty_ShouldDeleteProperty_WhenIdIsValid() {
        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(1L)
                .address(AddressEntity.builder().id(1L).build())
                .build();
        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.of(propertyEntity));
        doNothing().when(propertyRepositoryMock).deleteById(1L);

        propertyService.deleteProperty(1);

        verify(propertyRepositoryMock).deleteById(1L);
    }

    @Test
    void deleteProperty_ShouldReturnNotFound_WhenIdIsInvalid() {
        when(propertyRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.deleteProperty(99));
    }
}