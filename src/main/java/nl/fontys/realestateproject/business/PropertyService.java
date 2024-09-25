package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.domain.Property.*;

public interface PropertyService {
    CreatePropertyResponse createProperty(CreatePropertyRequest request);
    GetAllPropertiesResponse getAllProperties();
    GetPropertyResponse getProperty(long id);
    void updateProperty(UpdatePropertyRequest request);
    void deleteProperty(int id);
}
