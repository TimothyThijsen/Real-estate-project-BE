package nl.fontys.realestateproject.business.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.ListingByRoomSize;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetListingStatusByRoomSizeResponse {
    List<ListingByRoomSize> demandsByRoomSize;
}
