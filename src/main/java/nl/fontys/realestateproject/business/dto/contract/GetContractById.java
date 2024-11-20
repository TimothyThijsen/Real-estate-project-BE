package nl.fontys.realestateproject.business.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Contract;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetContractById {
    Contract contract;
}
