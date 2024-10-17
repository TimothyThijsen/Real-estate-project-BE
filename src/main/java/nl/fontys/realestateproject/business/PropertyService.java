package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.DTO.Property.*;

public interface PropertyService {
    CreatePropertyResponse createProperty(CreatePropertyRequest request);
    GetAllPropertiesResponse getAllProperties();
    GetPropertyResponse getProperty(long id);
    void updateProperty(UpdatePropertyRequest request);
    void deleteProperty(long id);
}
