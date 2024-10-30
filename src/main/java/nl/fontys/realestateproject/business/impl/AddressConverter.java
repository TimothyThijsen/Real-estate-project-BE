package nl.fontys.realestateproject.business.impl;

import nl.fontys.realestateproject.domain.Address;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import org.springframework.stereotype.Service;

@Service
final class AddressConverter {
    public Address convert(AddressEntity addressEntity) {
        return Address.builder()
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .country(addressEntity.getCountry())
                .build();
    }
}
