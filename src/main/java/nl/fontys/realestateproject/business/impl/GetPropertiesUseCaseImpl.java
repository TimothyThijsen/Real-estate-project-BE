package nl.fontys.realestateproject.business.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.GetPropertiesUseCase;
import nl.fontys.realestateproject.domain.GetAllPropertiesResponse;
import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.implementations.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPropertiesUseCaseImpl implements GetPropertiesUseCase {
    private final PropertyRepository propertyRepository;


    @Override
    public GetAllPropertiesResponse getAllProperties() {
        List<PropertyEntity> results = propertyRepository.GetProperties();

        final GetAllPropertiesResponse response = new GetAllPropertiesResponse();
        List<Property> properties = results
                .stream()
                .map(PropertyConverter::convert)
                .toList();
        response.setProperties(properties);
        return response;
    }
}
