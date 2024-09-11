package nl.fontys.realestateproject.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePropertyResponse {
    private Long propertyId;
}
