package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.dto.property.*;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.persistence.AddressRepository;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.PropertySurfaceAreaRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {
    @Mock
    PropertyRepository propertyRepositoryMock;
    @Mock
    AddressRepository addressRepositoryMock;
    @Mock
    PropertySurfaceAreaRepository propertySurfaceAreaRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    PropertyConverter propertyConverterMock;
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
                .surfaceAreas(List.of(PropertySurfaceArea.builder().nameOfSurfaceArea("Living room").areaInSquareMetre(100.0).build()))
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
    void createProperty_ShouldThrowDataIntegrityException_WhenStreetAddressIsAlreadyInUse() {
        CreatePropertyRequest request = CreatePropertyRequest.builder()
                .description("Nice lake side villa")
                .street("Street")
                .country("Netherlands")
                .city("Eidnhoven")
                .postalCode("1234AB")
                .propertyType("RESIDENTIAL")
                .listingType("SALE")
                .surfaceAreas(List.of(PropertySurfaceArea.builder().nameOfSurfaceArea("Living room").areaInSquareMetre(100.0).build()))
                .build();

        when(propertyRepositoryMock.save(any(PropertyEntity.class)))
                .thenThrow(new DataIntegrityViolationException("Street address is already in use"));

        assertThrows(InvalidPropertyException.class, () -> propertyService.createProperty(request));
    }

    @Test
    void createProperty_ShouldThrowException_WhenRequestIsInvalid() {
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
        when(propertyRepositoryMock.save(any(PropertyEntity.class)))
                .thenThrow(new InvalidPropertyException("Invalid request"));
        assertThrows(InvalidPropertyException.class, () -> propertyService.createProperty(request));

        // Assert: Check the exception message
    }
    @Test
    void getAllProperties() {
        PropertyEntity property1 = PropertyEntity.builder().id(1L).description("Property 1").surfaceAreas(List.of()).build();
        PropertyEntity property2 = PropertyEntity.builder().id(2L).description("Property 2").surfaceAreas(List.of()).build();

        when(propertyRepositoryMock.findAllAvailableProperty()).thenReturn(List.of(property1, property2));
        when(propertyConverterMock.convert(property1)).thenReturn(Property.builder().id(1L).description("Property 1").build());
        when(propertyConverterMock.convert(property2)).thenReturn(Property.builder().id(2L).description("Property 2").build());
        GetAllPropertiesResponse actual = propertyService.getAllProperties();

        assertEquals(2, actual.getProperties().size());
        verify(propertyRepositoryMock).findAllAvailableProperty();
    }

    @Test
    void getProperty_ShouldReturnProperty() {
        PropertyEntity property2 = PropertyEntity.builder().id(2L).description("Property 2").surfaceAreas(List.of()).build();

        when(propertyRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(property2));
        when(propertyConverterMock.convert(property2)).thenReturn(Property.builder().id(2L).description("Property 2").build());
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
                .surfaceAreas(List.of(PropertySurfaceArea.builder().nameOfSurfaceArea("Living Room").areaInSquareMetre(100.0).build()))
                .build();


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
        AccountEntity accountEntity = AccountEntity.builder().id(1L).build();
        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(1L)
                .account(accountEntity)
                .address(AddressEntity.builder().id(1L).build())
                .build();
        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.of(propertyEntity));
        doNothing().when(propertyRepositoryMock).deleteById(1L);

        propertyService.deleteProperty(1,1);

        verify(propertyRepositoryMock).deleteById(1L);
    }

    @Test
    void deleteProperty_ShouldReturnNotFound_WhenIdIsInvalid() {
        when(propertyRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidPropertyException.class, () -> propertyService.deleteProperty(99,1));
    }
    @Test
    void deleteProperty_ShouldReturnNotFound_WhenAccountIdIsInvalid() {
        AccountEntity accountEntity = AccountEntity.builder().id(1L).build();
        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(1L)
                .account(accountEntity)
                .address(AddressEntity.builder().id(1L).build())
                .account(accountEntity)
                .build();
        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.of(propertyEntity));
        assertThrows(ResponseStatusException.class, () -> propertyService.deleteProperty(1,99));
    }
}