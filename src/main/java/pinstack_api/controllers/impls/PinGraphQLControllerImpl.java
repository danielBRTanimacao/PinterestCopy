package pinstack_api.controllers.impls;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pinstack_api.controllers.PinGraphQLController;
import pinstack_api.entities.PinEntity;

@RestController
@RequiredArgsConstructor
public class PinGraphQLControllerImpl implements PinGraphQLController {

    @Override
    public List<PinEntity> mainFeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mainFeed'");
    }

    @Override
    public PinEntity pinById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pinById'");
    }

    @Override
    public PinEntity createPin(String title, String description, String imageUrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPin'");
    }

    @Override
    public int likePin(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likePin'");
    }

    
}