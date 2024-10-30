package nl.fontys.realestateproject.business.dto.property;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePropertyResponse {
    private Long propertyId;
}
