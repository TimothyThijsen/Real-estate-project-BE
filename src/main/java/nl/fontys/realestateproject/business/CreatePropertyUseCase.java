package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.domain.CreatePropertyRequest;
import nl.fontys.realestateproject.domain.CreatePropertyResponse;
import nl.fontys.realestateproject.domain.Property;

public interface CreatePropertyUseCase {
    CreatePropertyResponse CreateProperty(CreatePropertyRequest request);
}
