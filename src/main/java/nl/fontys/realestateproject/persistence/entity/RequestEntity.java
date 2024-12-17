package nl.fontys.realestateproject.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "request")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @JoinColumn(name = "account_id")
    private Long accountId;

    @Column(name = "date")
    private LocalDateTime requestDate;
    @Column (name = "status")
    private String requestStatus;
}
