package pinstack_api.controllers.impls;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
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
    @QueryMapping
    @Cacheable(value = "pinsFeed")
    public List<ResponsePinDTO> mainFeed() {
        log.info("Fetching main feed pins");
        return service.getAllPins();
    }

    @Override
    @QueryMapping
    public ResponsePinDTO pinById(@Argument String id) {
        return service.getSpecificPin(id);
    }

    @Override
    @MutationMapping
    @CacheEvict(value = "pinsFeed", allEntries = true)
    public ResponsePinDTO createPin(@Argument @Valid RequestPinDTO data) {
        return service.savePin(data);
    }

    @Override
    @MutationMapping
    public int likePin(@Argument String id) {
        return service.likePinById(id);
    }
}