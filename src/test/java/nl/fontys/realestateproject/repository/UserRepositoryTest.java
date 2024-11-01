package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void save_shouldSaveUserWithAllFields() {
        // Arrange
        AccountEntity user = AccountEntity.builder()
                .firstName("test")
                .lastName("test")
                .role(UserRole.CLIENT)
                .email("email")
                .password("test")
                .build();

        // Act
        AccountEntity savedUser = userRepository.save(user);

        AccountEntity expectedUser = AccountEntity.builder().id(savedUser.getId()).firstName("test").lastName("test").role(UserRole.CLIENT).email("email").password("test").build();

        assertEquals(expectedUser, savedUser);
        // Assert
        assertNotNull(savedUser);
        assertEquals("email", savedUser.getEmail());

    }

    @Test
    void find_shouldReturnUser_WhenTheyExist() {
        // Arrange
        AccountEntity user = AccountEntity.builder()
                .firstName("name")
                .lastName("test")
                .role(UserRole.CLIENT)
                .email("email")
                .password("test")
                .build();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();
        // Act
        AccountEntity savedUser = entityManager.find(AccountEntity.class, user.getId());
        // Assert
        assertNotNull(savedUser);
        assertEquals("email", savedUser.getEmail());
    }

    @Test
    void find_shouldReturnAllUsers_WhenTheyExist() {
        // Arrange
        AccountEntity user1 = AccountEntity.builder()
                .firstName("name")
                .lastName("test")
                .role(UserRole.CLIENT)
                .email("email1")
                .password("test")
                .build();
        AccountEntity user2 = AccountEntity.builder()
                .firstName("name")
                .lastName("test")
                .role(UserRole.CLIENT)
                .email("email2")
                .password("test")
                .build();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
        entityManager.clear();
        // Act
        var users = userRepository.findAll();
        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
    }

}
