package nl.fontys.realestateproject.business;

import nl.fontys.realestateproject.business.dto.contract.GetAllContractsResponse;
import nl.fontys.realestateproject.business.dto.contract.GetContractById;

public interface ContractService {
    void cancelContract(long id);

    GetContractById getContractById(long id);

    GetAllContractsResponse getAllContractsByAgentId(long agentId);

    GetAllContractsResponse getAllContractsByCustomerId(long customerId);

}
