package nl.fontys.realestateproject.repository;

import jakarta.persistence.EntityManager;
import nl.fontys.realestateproject.domain.enums.UserRole;
import nl.fontys.realestateproject.persistence.UserRepository;
import nl.fontys.realestateproject.persistence.entity.AccountEntity;
import nl.fontys.realestateproject.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

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
                .email("email")
                .password("test")
                .build();
        UserRoleEntity userRoles = UserRoleEntity.builder().role(UserRole.CLIENT).user(user).build();
        user.setUserRoles(Set.of(userRoles));
        // Act
        AccountEntity savedUser = userRepository.save(user);

        AccountEntity expectedUser = AccountEntity.builder().id(savedUser.getId()).firstName("test").lastName("test").userRoles(Set.of(userRoles)).email("email").password("test").build();

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
                .email("email")
                .password("test")
                .build();
        UserRoleEntity userRoles = UserRoleEntity.builder().role(UserRole.CLIENT).user(user).build();
        user.setUserRoles(Set.of(userRoles));
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
                .email("email1")
                .password("test")
                .build();
        UserRoleEntity userRoles1 = UserRoleEntity.builder().role(UserRole.CLIENT).user(user1).build();
        user1.setUserRoles(Set.of(userRoles1));

        AccountEntity user2 = AccountEntity.builder()
                .firstName("name")
                .lastName("test")
                .email("email2")
                .password("test")
                .build();
        UserRoleEntity userRoles2 = UserRoleEntity.builder().role(UserRole.CLIENT).user(user2).build();
        user2.setUserRoles(Set.of(userRoles2));

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
