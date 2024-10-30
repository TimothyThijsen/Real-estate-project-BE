package nl.fontys.realestateproject.business.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Property;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPropertyResponse {
    Property property;
}
