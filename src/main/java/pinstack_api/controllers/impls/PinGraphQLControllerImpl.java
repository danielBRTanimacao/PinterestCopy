package pinstack_api.controllers.impls;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pinstack_api.DTOs.*;
import pinstack_api.controllers.PinGraphQLController;
import pinstack_api.services.PinService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PinGraphQLControllerImpl implements PinGraphQLController {

    private final PinService service;

    @Override
    @Cacheable(value = "pinsFeed")
    public List<ResponsePinDTO> mainFeed() {
        log.info("Fetching main feed pins");
        return service.getAllPins();
    }

    @Override
    public ResponsePinDTO pinById(String id) {
        return service.getSpecificPin(id);
    }

    @Override
    @CacheEvict(value = "pinsFeed", allEntries = true)
    public ResponsePinDTO createPin(RequestPinDTO data) {
        return service.savePin(data);
    }

    @Override
    public int likePin(String id) {
        return service.likePinById(id);
    }
    
}