package nl.fontys.realestateproject.domain.Property;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePropertyResponse {
    private boolean isUpdated;
}
