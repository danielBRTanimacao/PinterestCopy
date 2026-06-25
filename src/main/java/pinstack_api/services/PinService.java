package pinstack_api.services;

import java.util.List;

import pinstack_api.entities.PinEntity;

public interface PinService {
    List<PinEntity> getAllPins();

    PinEntity getSpecificPin(String id);

    PinEntity savePin(String title, String description, String imageUrl);

    int likePinById(String id);
}
