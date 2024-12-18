package nl.fontys.realestateproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Request {
    private long id;
    private Property property;
    private Account account;
    private RequestStatus status;
    private LocalDateTime requestDate;
}
