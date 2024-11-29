package nl.fontys.realestateproject.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.domain.Request;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestResponse {
    Request request;
}
