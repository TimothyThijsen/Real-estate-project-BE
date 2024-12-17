package nl.fontys.realestateproject.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetActiveRequestsResponse {
    private boolean hasActiveRequests;
}
