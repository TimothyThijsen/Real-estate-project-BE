package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "property")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 250)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "listing_type")
    private ListingType listingType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @OneToOne
    @NotNull
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "property")
    private List<PropertySurfaceAreaEntity> surfaceAreas;
}
