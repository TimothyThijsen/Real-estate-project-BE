package nl.fontys.realestateproject.persistence.implementations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static long NEXT_ID = 1;
   private List<AccountEntity> savedAccounts;

    public UserRepositoryImpl() {
        this.savedAccounts = new ArrayList<>();
    }

    @Override
    public Optional<AccountEntity> GetAccount(long id) {
        return Optional.empty();
    }

    @Override
    public AccountEntity CreateAccount(AccountEntity property) {
        property.setId(NEXT_ID);
        savedAccounts.add(property);
        NEXT_ID++;
        return property;
    }

    @Override
    public boolean UpdateAccount(AccountEntity property) {
        return false;
    }

    @Override
    public boolean DeleteAccount(long accountId) {
        return false;
    }

    @Override
    public List<AccountEntity> GetAllAccounts() {
        return savedAccounts;
    }
}
