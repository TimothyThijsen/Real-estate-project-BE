package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    private long id;
    private Property property;
    private long customerId;
    private boolean active;
    private LocalDateTime minimumContractEndDate;
    private LocalDateTime startDate;
    private List<Transaction> transactions;
}
