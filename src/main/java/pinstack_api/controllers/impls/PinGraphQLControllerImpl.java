package pinstack_api.controllers.impls;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pinstack_api.controllers.PinGraphQLController;
import pinstack_api.entities.PinEntity;
import pinstack_api.services.PinService;

@Controller
@RequiredArgsConstructor
public class PinGraphQLControllerImpl implements PinGraphQLController {

    private final PinService service;

    @Override
    public List<PinEntity> mainFeed() {
        return service.getAllPins();
    }

    @Override
    public PinEntity pinById(String id) {
        return service.getSpecificPin(id);
    }

    @Override
    public PinEntity createPin(String title, String description, String imageUrl) {
        return service.savePin(title, description, imageUrl);
    }

    @Override
    public int likePin(String id) {
        return service.likePinById(id);
    }

    
}