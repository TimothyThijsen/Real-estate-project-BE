package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.business.dto.property.*;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.persistence.AddressRepository;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.PropertySurfaceAreaRepository;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    PropertyRepository propertyRepository;
    AddressRepository addressRepository;
    PropertySurfaceAreaRepository surfaceAreaRepository;
    PropertyConverter propertyConverter;

    @Override
    @Transactional
    public CreatePropertyResponse createProperty(CreatePropertyRequest request) {
        PropertyEntity savedProperty = createNewProperty(request);

        return CreatePropertyResponse.builder()
                .propertyId(savedProperty.getId())
                .build();
    }

    private PropertyEntity createNewProperty(CreatePropertyRequest request) {
        AddressEntity address = saveAddress(request.getCity(), request.getCountry(), request.getPostalCode(), request.getStreet());
        addressRepository.save(address);

        PropertyEntity newProperty = PropertyEntity.builder()
                .description(request.getDescription())
                .price(request.getPrice())
                .address(address)
                .propertyType(PropertyType.valueOf(request.getPropertyType()))
                .listingType(ListingType.valueOf(request.getListingType()))
                .build();

        List<PropertySurfaceAreaEntity> surfaceAreas = request.getSurfaceAreas().stream()
                .map(surfaceArea -> PropertySurfaceAreaEntity.builder()
                        .nameOfSurfaceArea(surfaceArea.getNameOfSurfaceArea())
                        .areaInSquareMetre(surfaceArea.getAreaInSquareMetre())
                        .property(newProperty)
                        .build())
                .toList();

        newProperty.setSurfaceAreas(surfaceAreas);
        surfaceAreaRepository.saveAll(surfaceAreas);

        return propertyRepository.save(newProperty);
    }

    private AddressEntity saveAddress(String city, String country, String postalCode, String street) {
        return AddressEntity.builder()
                .city(city)
                .country(country)
                .postalCode(postalCode)
                .street(street)
                .build();
    }

    @Override
    public GetAllPropertiesResponse getAllProperties() {
        List<PropertyEntity> results = propertyRepository.findAll();

        final GetAllPropertiesResponse response = new GetAllPropertiesResponse();
        List<Property> properties = results
                .stream()
                .map(propertyConverter::convert)
                .toList();
        response.setProperties(properties);
        return response;
    }

    @Override
    public GetPropertyResponse getProperty(long id) {
        Optional<PropertyEntity> result = propertyRepository.findById(id);
        if (result.isEmpty()) {
            throw new InvalidPropertyException();
        }
        return GetPropertyResponse.builder()
                .property(propertyConverter.convert(result.get()))
                .build();
    }

    @Override
    @Transactional
    public void updateProperty(UpdatePropertyRequest request) {
        if (!propertyRepository.existsById(request.getId())) {
            throw new InvalidPropertyException();
        }
        propertyRepository.save(getUpdatedPropertyEntity(request));
    }

    private PropertyEntity getUpdatedPropertyEntity(UpdatePropertyRequest request) {
        AddressEntity address = saveAddress(request.getCity(), request.getCountry(), request.getPostalCode(), request.getStreet());
        addressRepository.save(address);//change so that street is key

        List<PropertySurfaceAreaEntity> surfaceAreas = request.getSurfaceAreas().stream()
                .map(surfaceArea -> PropertySurfaceAreaEntity.builder()
                        .nameOfSurfaceArea(surfaceArea.getNameOfSurfaceArea())
                        .areaInSquareMetre(surfaceArea.getAreaInSquareMetre())
                        .build())
                .toList();

        surfaceAreaRepository.saveAll(surfaceAreas);
        return PropertyEntity.builder()
                .id(request.getId())
                .description(request.getDescription())
                .price(request.getPrice())
                .address(address)
                .propertyType(PropertyType.valueOf(request.getPropertyType()))
                .listingType(ListingType.valueOf(request.getListingType()))
                .surfaceAreas(surfaceAreas)
                .build();
    }

    @Override
    @Transactional
    public void deleteProperty(long id) {
        Optional<PropertyEntity> propertyEntity = propertyRepository.findById(id);
        if (propertyEntity.isEmpty()) {
            throw new InvalidPropertyException();
        }
        surfaceAreaRepository.deleteAllByPropertyId(id);
        propertyRepository.deleteById(id);
        addressRepository.deleteById(propertyEntity.get().getAddress().getId());
    }

}
