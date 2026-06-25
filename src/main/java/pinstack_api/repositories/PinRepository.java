package pinstack_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import pinstack_api.entities.Pin;

public interface PinRepository extends MongoRepository<Pin, String> {
    
}
