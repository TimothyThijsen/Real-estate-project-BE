package nl.fontys.realestateproject.business.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllPropertiesBySearchRequest {
    private String searchTerm;
    private String listingType;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Double minTotalArea;
    private Integer currentPage;
    private Integer pageSize;

}
