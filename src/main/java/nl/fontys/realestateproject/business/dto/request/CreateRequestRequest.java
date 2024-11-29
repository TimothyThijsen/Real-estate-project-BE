package nl.fontys.realestateproject.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestRequest {
    private long propertyId;
    private long accountId;
    private String requestStatus;
}
