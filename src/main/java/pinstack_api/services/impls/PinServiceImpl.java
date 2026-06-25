package pinstack_api.services.impls;

import java.util.List;

import org.springframework.stereotype.Service;

import pinstack_api.DTOs.*;
import pinstack_api.services.PinService;

@Service
public class PinServiceImpl implements PinService{

    @Override
    public List<ResponsePinDTO> getAllPins() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllPins'");
    }

    @Override
    public ResponsePinDTO getSpecificPin(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'getSpecificPin'");
    }

    @Override
    public ResponsePinDTO savePin(RequestPinDTO data) {
        throw new UnsupportedOperationException("Unimplemented method 'savePin'");
    }

    @Override
    public int likePinById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'likePinById'");
    }
    
}
