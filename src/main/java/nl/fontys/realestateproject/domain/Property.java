package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Property {
    private int id;
    private String name;
    private String description;

}
