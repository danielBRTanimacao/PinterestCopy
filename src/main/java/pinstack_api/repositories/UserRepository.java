package pinstack_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import pinstack_api.entities.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsernameOrEmail(String usernameOrMail);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
