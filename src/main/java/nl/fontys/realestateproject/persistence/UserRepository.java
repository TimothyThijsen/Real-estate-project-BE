package nl.fontys.realestateproject.persistence;

import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<AccountEntity> GetAccount(long id);

    AccountEntity CreateAccount(AccountEntity property);

    boolean UpdateAccount(AccountEntity property);

    boolean DeleteAccount(long accountId);

    List<AccountEntity> GetAllAccounts();

    boolean AccountExists(String email);
}
