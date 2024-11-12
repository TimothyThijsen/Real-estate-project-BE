package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.domain.enums.ListingType;
import nl.fontys.realestateproject.domain.enums.PropertyType;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.TransactionRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.AddressEntity;
import nl.fontys.realestateproject.persistence.entity.PropertyEntity;
import nl.fontys.realestateproject.persistence.entity.TransactionEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_shouldSaveTransactionWithAllFields() {
        TransactionEntity transaction = TransactionEntity.builder()
                .propertyId(1L)
                .customerId(1L)
                .date(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
        TransactionEntity savedTransaction = entityManager.find(TransactionEntity.class, transaction.getId());

        assertNotNull(savedTransaction);
        assertEquals(1L, savedTransaction.getPropertyId());
    }

    @AfterEach
    public void cleanUp() {
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                        "WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME != 'flyway_schema_history'",
                String.class
        );
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        tables.forEach(table -> {
            jdbcTemplate.execute("TRUNCATE TABLE " + table);
            jdbcTemplate.execute("ALTER TABLE " + table + " ALTER COLUMN id RESTART WITH 1");
        });
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @BeforeEach
    public void setupTestEnvironment() {
        AddressEntity address = AddressEntity.builder().street("Boschdijk").postalCode("1234AB").city("Eindhoven").country("Netherlands").build();
        entityManager.persist(address);
        PropertyEntity property = PropertyEntity.builder().listingType(ListingType.SALE)
                .propertyType(PropertyType.APARTMENT)
                .description("test")
                .price(BigDecimal.valueOf(1000))
                .address(address)
                .build();
        entityManager.persist(property);
        AccountEntity account = AccountEntity.builder().firstName("test")
                .lastName("test")
                .role(UserRole.CLIENT)
                .email("email")
                .password("test")
                .build();
        entityManager.persist(account);
    }

    @Test
    void find_shouldReturnTransaction_WhenItExists() {
        TransactionEntity transaction = TransactionEntity.builder()
                .propertyId(1L)
                .customerId(1L)
                .date(LocalDateTime.now())
                .build();
        entityManager.persist(transaction);
        entityManager.flush();
        entityManager.clear();
        TransactionEntity savedTransaction = entityManager.find(TransactionEntity.class, transaction.getId());

        assertNotNull(savedTransaction);
        assertEquals(1L, savedTransaction.getPropertyId());
    }

    @Test
    void findByCustomerId_shouldReturnTransaction_WhenItExists() {
        TransactionEntity transaction = TransactionEntity.builder()
                .propertyId(1L)
                .customerId(1L)
                .date(LocalDateTime.now())
                .build();
        entityManager.persist(transaction);
        entityManager.flush();
        entityManager.clear();
        TransactionEntity savedTransaction = transactionRepository.findAllByCustomerId(1L).get(0);

        assertNotNull(savedTransaction);
        assertEquals(1L, savedTransaction.getPropertyId());
    }

    @Test
    void findByPropertyId_shouldReturnTransaction_WhenItExists() {
        TransactionEntity transaction = TransactionEntity.builder()
                .propertyId(1L)
                .customerId(1L)
                .date(LocalDateTime.now())
                .build();
        entityManager.persist(transaction);
        entityManager.flush();
        entityManager.clear();
        TransactionEntity savedTransaction = transactionRepository.findAllByPropertyId(1L).get(0);

        assertNotNull(savedTransaction);
        assertEquals(1L, savedTransaction.getPropertyId());
    }
}
