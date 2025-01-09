package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingByRoomSize {
    private Double roomSize;
    private Map<String, Long> amountOfListing;
}
