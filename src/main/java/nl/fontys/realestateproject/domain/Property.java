package nl.fontys.realestateproject.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
 public class Property {
    private long id;
    private String name;
    private String description;

}
