package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contract")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;
    @JoinColumn(name = "customer_id")
    private Long customerId;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "minimum_contract_end_date")
    private LocalDateTime minimumContractEndDate;
    @Column(name = "startDate")
    private LocalDateTime startDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contract", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;
}
