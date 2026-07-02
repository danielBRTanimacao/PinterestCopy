package pinstack_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import pinstack_api.entities.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    
}
