package pinstack_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import pinstack_api.entities.PinEntity;

public interface PinRepository extends MongoRepository<PinEntity, String> {
    
}
