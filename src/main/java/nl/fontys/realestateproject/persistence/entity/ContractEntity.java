package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contract")
@Data
@ToString(exclude = {"property", "customer", "transactions"})
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
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AccountEntity customer;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "minimum_contract_end_date")
    private LocalDateTime minimumContractEndDate;
    @Column(name = "startDate")
    private LocalDateTime startDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contract", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;
}
