package nl.fontys.realestateproject.persistence.implementations;

import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
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
        return savedAccounts.stream()
                .filter(accountEntity -> accountEntity.getId() == id)
                .findFirst();
    }

    @Override
    public AccountEntity CreateAccount(AccountEntity property) {
        property.setId(NEXT_ID);
        savedAccounts.add(property);
        NEXT_ID++;
        return property;
    }

    @Override
    public void UpdateAccount(AccountEntity property) {
        for (int i = 0; i < savedAccounts.size(); i++) {
            if (savedAccounts.get(i).getId() == (property.getId())) {
                savedAccounts.set(i, property);
                return;
            }
        }
    }

    @Override
    public void DeleteAccount(long accountId) {
        savedAccounts.removeIf(accountEntity -> accountEntity.getId() == (accountId));
    }

    @Override
    public List<AccountEntity> GetAllAccounts() {
        return savedAccounts;
    }

    @Override
    public boolean AccountExists(String email) {
        return savedAccounts.stream().anyMatch(accountEntity -> accountEntity.getEmail().equals(email));
    }

    @Override
    public Optional<AccountEntity> GetAccountByEmail(String email) {
        return savedAccounts.stream()
                .filter(accountEntity -> accountEntity.getEmail().equals(email))
                .findFirst();
    }
}
