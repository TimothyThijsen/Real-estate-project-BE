package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "property_surface_area")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertySurfaceAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Length(min = 2, max = 50)
    @Column(name = "name_of_surface_area")
    private String nameOfSurfaceArea;
    @NotNull
    @Min(1)
    @Column(name = "area_in_square_metre")
    private Double areaInSquareMetre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;
}
