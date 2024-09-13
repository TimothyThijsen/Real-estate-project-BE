package nl.fontys.realestateproject.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class PropertyEntity {
    private Long id;
    private String name;
    private String description;
}
