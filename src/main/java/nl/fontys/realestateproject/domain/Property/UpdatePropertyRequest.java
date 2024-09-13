package nl.fontys.realestateproject.domain.Property;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePropertyRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
