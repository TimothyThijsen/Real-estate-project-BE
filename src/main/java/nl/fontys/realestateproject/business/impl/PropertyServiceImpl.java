package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.PropertyService;
import nl.fontys.realestateproject.business.dto.property.*;
import nl.fontys.realestateproject.business.exceptions.InvalidPropertyException;
import nl.fontys.realestateproject.domain.DemandByRoomSize;
import nl.fontys.realestateproject.domain.ListingByRoomSize;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.domain.enums.ListingStatus;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.persistence.AddressRepository;
import nl.fontys.realestateproject.persistence.PropertyRepository;
import nl.fontys.realestateproject.persistence.PropertySurfaceAreaRepository;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.PropertySurfaceAreaEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;


@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    PropertyRepository propertyRepository;
    AddressRepository addressRepository;
    PropertySurfaceAreaRepository surfaceAreaRepository;
    UserRepository userRepository;
    PropertyConverter propertyConverter;

    @Override
    @Transactional
    public CreatePropertyResponse createProperty(CreatePropertyRequest request) {
        PropertyEntity savedProperty;
        try {
            savedProperty = createNewProperty(request);

        } catch (DataIntegrityViolationException ex) {
            throw new InvalidPropertyException("Street address is already in use");
        } catch (Exception e) {
            throw new InvalidPropertyException("Error occurred trying to create property");
        }

        return CreatePropertyResponse.builder()
                .propertyId(savedProperty.getId())
                .build();
    }

    private PropertyEntity createNewProperty(CreatePropertyRequest request) {
        AddressEntity address = AddressEntity.builder()
                .city(request.getCity())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .street(request.getStreet())
                .build();
        addressRepository.save(address);
        AccountEntity account = userRepository.getReferenceById(request.getAgentId());
        PropertyEntity newProperty = PropertyEntity.builder()
                .description(request.getDescription())
                .price(request.getPrice())
                .address(address)
                .propertyType(PropertyType.valueOf(request.getPropertyType()))
                .listingType(ListingType.valueOf(request.getListingType()))
                .account(account)
                .imageUrl(request.getImageUrl())
                .listingStatus(ListingStatus.ACTIVE.toString())
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

    @Override
    public GetAllPropertiesResponse getAllProperties() {
        List<PropertyEntity> results = propertyRepository.findAllAvailableProperty();

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
        PropertyEntity existingProperty = propertyRepository.findById(request.getId()).orElseThrow(InvalidPropertyException::new);
        AddressEntity address = AddressEntity.builder()
                .id(existingProperty.getAddress().getId())
                .city(request.getCity())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .street(request.getStreet())
                .build();

        addressRepository.save(address);//change so that street is key

        surfaceAreaRepository.deleteAllByPropertyId(request.getId());
        List<PropertySurfaceAreaEntity> surfaceAreas = request.getSurfaceAreas().stream()
                .map(surfaceArea -> PropertySurfaceAreaEntity.builder()
                        .nameOfSurfaceArea(surfaceArea.getNameOfSurfaceArea())
                        .areaInSquareMetre(surfaceArea.getAreaInSquareMetre())
                        .property(propertyRepository.getReferenceById(request.getId()))
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
                .account(userRepository.getReferenceById(request.getAgentId()))
                .listingStatus(existingProperty.getListingStatus())
                .build();
    }

    @Override
    @Transactional
    public void deleteProperty(long id, long agentId) {
        Optional<PropertyEntity> propertyEntity = propertyRepository.findById(id);
        if (propertyEntity.isEmpty()) {
            throw new InvalidPropertyException();
        }
        if (propertyEntity.get().getAccount().getId() != agentId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        surfaceAreaRepository.deleteAllByPropertyId(id);
        propertyRepository.deleteById(id);
        addressRepository.deleteById(propertyEntity.get().getAddress().getId());
    }

    @Override
    public GetAllPropertiesByAgentId getAllPropertiesByAgentId(long agentId) {
        List<PropertyEntity> results = propertyRepository.findAllByAccountId(agentId);
        return GetAllPropertiesByAgentId.builder()
                .properties(results.stream().map(propertyConverter::convert).toList())
                .build();
    }

    @Override
    public GetAllPropertiesResponse getAllPropertiesBySearch(GetAllPropertiesBySearchRequest request) {
        Pageable pageable = PageRequest.of(request.getCurrentPage()-1, request.getPageSize());

        Page<PropertyEntity> results = propertyRepository.findAllByAvailableAndSearchTerm(
                ListingType.valueOf(request.getListingType()),
                pageable,
                request.getMinPrice() != null ? request.getMinPrice() : BigDecimal.ZERO,
                request.getMaxPrice() != null ? request.getMaxPrice() : BigDecimal.ZERO,
                request.getSearchTerm() != null ? request.getSearchTerm() : "",
                request.getMinTotalArea() != null ? request.getMinTotalArea() : 0.0);

        final GetAllPropertiesResponse response = new GetAllPropertiesResponse();
        List<Property> properties = results
                .stream()
                .map(propertyConverter::convert)
                .toList();

        response.setProperties(properties);
        response.setTotalPages(results.getTotalPages());
        return response;
    }

    @Override
    public GetRoomSizeDemandResponse getRoomSizeDemand(long agentId) {
        List<DemandByRoomSize> results = propertyRepository.getAllRoomSizeByDemand(agentId);

        return GetRoomSizeDemandResponse.builder()
                .demandsByRoomSize(results)
                .build();
    }

    @Override
    public GetListingStatusByRoomSizeResponse getListingStatusByRoomSize(long agentId) {
        List<Double> roomSizes = propertyRepository.getRoomSizes(agentId);
        List<ListingByRoomSize> listingByRoomSizes = new ArrayList<>();

        for (Double roomSize : roomSizes) {
            Map<String, Long> results = new HashMap<>();
            Long activeListingStatusCount = propertyRepository.getListingStatusByRoomSize(agentId, roomSize, "ACTIVE");
            Long soldListingStatusCount = propertyRepository.getListingStatusByRoomSize(agentId, roomSize, "SOLD");
            Long rentedListingStatusCount = propertyRepository.getListingStatusByRoomSize(agentId, roomSize, "RENTED");
            results.put("ACTIVE", activeListingStatusCount);
            results.put("SOLD", soldListingStatusCount);
            results.put("RENTED", rentedListingStatusCount);

            listingByRoomSizes.add(ListingByRoomSize.builder()
                    .roomSize(roomSize)
                    .amountOfListing(results)
                    .build());
        }
        return GetListingStatusByRoomSizeResponse.builder()
                .demandsByRoomSize(listingByRoomSizes)
                .build();
    }


}
