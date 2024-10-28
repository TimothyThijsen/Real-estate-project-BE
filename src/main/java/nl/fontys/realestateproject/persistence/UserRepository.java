package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<AccountEntity, Long> {

        Optional<AccountEntity> findByEmail(String email);
}
