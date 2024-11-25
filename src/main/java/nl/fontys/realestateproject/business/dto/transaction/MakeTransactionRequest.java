package nl.fontys.realestateproject.business.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeTransactionRequest {
    private Long propertyId;
    private Long customerId;
}
