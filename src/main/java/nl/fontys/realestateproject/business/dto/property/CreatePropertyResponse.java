package nl.fontys.realestateproject.business.DTO.Property;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePropertyResponse {
    private Long propertyId;
}
