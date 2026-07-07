package pinstack_api.services;

import java.util.List;

import pinstack_api.DTOs.*;


public interface PinService {
    List<ResponsePinDTO> getAllPins();
    ResponsePinDTO getSpecificPin(String id);
    ResponsePinDTO savePin(RequestPinDTO data, String id);
    int likePinById(String id, String userId);
}
