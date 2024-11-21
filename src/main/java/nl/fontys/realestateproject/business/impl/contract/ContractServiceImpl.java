package nl.fontys.realestateproject.business.impl.contract;

import lombok.AllArgsConstructor;
import nl.fontys.realestateproject.business.ContractService;
import nl.fontys.realestateproject.business.dto.contract.GetAllContractsResponse;
import nl.fontys.realestateproject.business.dto.contract.GetContractById;
import nl.fontys.realestateproject.business.exceptions.ContractNotFoundException;
import nl.fontys.realestateproject.persistence.ContractRepository;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    ContractConverter contractConverter;
    @Override
    public void cancelContract(long id) {
        contractRepository.cancelContract(id);
    }

    @Override
    public GetContractById getContractById(long id) {
        Optional<ContractEntity> contractOptional = contractRepository.findById(id);
        if (contractOptional.isEmpty()) {
            throw new ContractNotFoundException();
        }
        return new GetContractById(contractConverter.convert(contractOptional.get()));
    }

    @Override
    public GetAllContractsResponse getAllContractsByAgentId(long agentId) {
        GetAllContractsResponse getAllContractsResponse = new GetAllContractsResponse();
        getAllContractsResponse.setContracts(
                contractRepository.findAllByAgentId(agentId).stream().map(contractConverter::convert).toList());
        return getAllContractsResponse;
    }

    @Override
    public GetAllContractsResponse getAllContractsByCustomerId(long customerId) {
        GetAllContractsResponse getAllContractsResponse = new GetAllContractsResponse();
        getAllContractsResponse.setContracts(
                contractRepository.findAllByCustomerId(customerId).stream().map(contractConverter::convert).toList());
        return getAllContractsResponse;
    }
}
