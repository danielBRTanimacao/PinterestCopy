package pinstack_api.controllers.impls;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import pinstack_api.DTOs.*;
import pinstack_api.controllers.PinGraphQLController;
import pinstack_api.services.PinService;

@Controller
@RequiredArgsConstructor
public class PinGraphQLControllerImpl implements PinGraphQLController {

    private final PinService service;

    @Override
    public List<ResponsePinDTO> mainFeed() {
        return service.getAllPins();
    }

    @Override
    public ResponsePinDTO pinById(String id) {
        return service.getSpecificPin(id);
    }

    @Override
    public ResponsePinDTO createPin(RequestPinDTO data) {
        return service.savePin(data);
    }

    @Override
    public int likePin(String id) {
        return service.likePinById(id);
    }
    
}