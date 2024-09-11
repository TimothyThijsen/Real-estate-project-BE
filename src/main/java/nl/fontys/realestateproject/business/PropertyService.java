package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.domain.Property;
import nl.fontys.realestateproject.persistence.IPropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    private List<Property> properties;
    private final IPropertyRepository propertyRepository;

    public PropertyService(IPropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /*public void CreateProperty(Property property) {
        propertyRepository.CreateProperty(property);
    }*/

    public void UpdateProperty(Property property) {
        propertyRepository.UpdateProperty(property);
    }

    public void DeleteProperty(Property property) {
        propertyRepository.DeleteProperty(property);
    }
    /*public List<Property> GetAllProperties() {
        return propertyRepository.GetProperties();
    }*/
    public Property GetProperty(int propertyId) {
        return propertyRepository.GetProperty(propertyId);
    }
}
