package nl.fontys.realestateproject.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.CreatePropertyUseCase;
import nl.fontys.realestateproject.domain.CreatePropertyRequest;
import nl.fontys.realestateproject.domain.CreatePropertyResponse;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.implementations.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatePropertyUseCaseImpl implements CreatePropertyUseCase {
    private final PropertyRepository propertyRepository;

    @Override
    public CreatePropertyResponse CreateProperty(CreatePropertyRequest request) {
        PropertyEntity savedProperty = createNewProperty(request);

        return CreatePropertyResponse.builder()
                .propertyId(savedProperty.getId())
                .build();
    }
    private PropertyEntity createNewProperty(CreatePropertyRequest request) {
        PropertyEntity newProperty = PropertyEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return propertyRepository.CreateProperty(newProperty);
    }
}
