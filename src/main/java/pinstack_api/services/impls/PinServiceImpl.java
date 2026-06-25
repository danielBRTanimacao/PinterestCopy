package pinstack_api.services.impls;

import java.util.List;

import org.springframework.stereotype.Service;

import pinstack_api.entities.PinEntity;
import pinstack_api.services.PinService;

@Service
public class PinServiceImpl implements PinService{

    @Override
    public List<PinEntity> getAllPins() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPins'");
    }

    @Override
    public PinEntity getSpecificPin(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpecificPin'");
    }

    @Override
    public PinEntity savePin(String title, String description, String imageUrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePin'");
    }

    @Override
    public int likePinById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likePinById'");
    }
    
}
