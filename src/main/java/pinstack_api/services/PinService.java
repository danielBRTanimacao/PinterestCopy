package pinstack_api.services;

import java.util.List;

import pinstack_api.DTOs.RequestPinDTO;
import pinstack_api.entities.PinEntity;

public interface PinService {
    List<PinEntity> getAllPins();
    PinEntity getSpecificPin(String id);
    PinEntity savePin(RequestPinDTO data);
    int likePinById(String id);
}
