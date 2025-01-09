package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemandByRoomSize {
    private Double roomSize;
    private Long amountOfRequests;
}
