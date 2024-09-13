package nl.fontys.realestateproject.domain.Property;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeletePropertyResponse {
    boolean propertyRemoved;
}
