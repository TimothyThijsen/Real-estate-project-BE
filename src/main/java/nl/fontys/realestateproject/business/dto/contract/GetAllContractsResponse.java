package nl.fontys.realestateproject.business.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Contract;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllContractsResponse {
    List<Contract> contracts;
}
