package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.business.impl.contract.ContractConverter;
import nl.fontys.realestateproject.business.impl.contract.ContractServiceImpl;
import nl.fontys.realestateproject.domain.Contract;
import nl.fontys.realestateproject.persistence.ContractRepository;
import nl.fontys.realestateproject.persistence.entity.ContractEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceImplTest {
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private ContractConverter contractConverter;
    @InjectMocks
    private ContractServiceImpl contractService;

    @Test
    void cancelContract_shouldCallRepository() {
        contractService.cancelContract(1L);
        verify(contractRepository).cancelContract(1L);
    }
    @Test
    void getContractById_shouldReturnContract() {
        ContractEntity contractEntity = ContractEntity.builder().id(1L).build();

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contractEntity));
        when(contractConverter.convert(any(ContractEntity.class))).thenReturn(Contract.builder().id(1L).build());
        contractService.getContractById(1L);
        verify(contractRepository).findById(1L);
        assertEquals(1L, contractService.getContractById(1L).getContract().getId());
    }
    @Test
    void getAllContractsByAgentId_shouldCallRepository() {
        contractService.getAllContractsByAgentId(1L);
        verify(contractRepository).findAllByAgentId(1L);
    }
    @Test
    void getAllContractsByCustomerId_shouldCallRepository() {
        contractService.getAllContractsByCustomerId(1L);
        verify(contractRepository).findAllByCustomerId(1L);
    }
}
