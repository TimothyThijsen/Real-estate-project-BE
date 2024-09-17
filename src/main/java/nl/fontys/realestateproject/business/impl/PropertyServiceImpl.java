package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.domain.ListingType;
import nl.fontys.realestateproject.domain.Property.*;
import nl.fontys.realestateproject.domain.PropertySurfaceArea;
import nl.fontys.realestateproject.domain.PropertyType;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public CreatePropertyResponse createProperty(CreatePropertyRequest request) {
        PropertyEntity savedProperty = createNewProperty(request);

        return CreatePropertyResponse.builder()
                .propertyId(savedProperty.getId())
                .build();
    }
    private PropertyEntity createNewProperty(CreatePropertyRequest request) {
        Address address = Address.builder()
                .city(request.getCity())
                .Country(request.getCountry())
                .PostalCode(request.getPostalCode())
                .street(request.getStreet())
                .build();

        PropertyEntity newProperty = PropertyEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .address(address)
                .propertyType(PropertyType.valueOf(request.getPropertyType()))
                .listingType(ListingType.valueOf(request.getListingType()))
                .surfaceAreas(request.getSurfaceAreas())
                .build();
        return propertyRepository.CreateProperty(newProperty);
    }

    @Override
    public GetAllPropertiesResponse getAllProperties() {
        List<PropertyEntity> results = propertyRepository.GetProperties();

        final GetAllPropertiesResponse response = new GetAllPropertiesResponse();
        List<Property> properties = results
                .stream()
                .map(propertyEntity -> PropertyConverter.convert(propertyEntity))
                .toList();
        response.setProperties(properties);
        return response;
    }

    @Override
    public GetPropertyResponse getProperty(long id) {
        Optional<PropertyEntity> result = propertyRepository.GetProperty(id);
        return GetPropertyResponse.builder().property(PropertyConverter.convert(result.get())).build();
    }

    @Override
    public UpdatePropertyResponse updateProperty(UpdatePropertyRequest request) {
        PropertyEntity updatedProperty = updateFields(request);

        return UpdatePropertyResponse.builder()
                .isUpdated(propertyRepository.UpdateProperty(updatedProperty))
                .build();
    }
    private PropertyEntity updateFields(UpdatePropertyRequest request) {
        return PropertyEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    @Override
    public DeletePropertyResponse deleteProperty(int id) {
       return DeletePropertyResponse.builder().propertyRemoved(propertyRepository.DeleteProperty(id)).build();
    }

}
