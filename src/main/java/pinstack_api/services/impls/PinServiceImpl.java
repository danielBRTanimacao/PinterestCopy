package pinstack_api.services.impls;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pinstack_api.DTOs.*;
import pinstack_api.entities.PinEntity;
import pinstack_api.exceptions.raises.NotFoundException;
import pinstack_api.repositories.PinRepository;
import pinstack_api.services.PinService;

@Service
@RequiredArgsConstructor
public class PinServiceImpl implements PinService{

    private final PinRepository repository;

    @Override
    public List<ResponsePinDTO> getAllPins() {
        return repository.findAll().stream().map(ResponsePinDTO::fromEntity).toList();
    }

    @Override
    public ResponsePinDTO getSpecificPin(String id) {
        PinEntity data = repository.findById(id).orElseThrow(() -> new NotFoundException("Pin with this id not found " + id));
        return ResponsePinDTO.fromEntity(data);
    }

    @Override
    public ResponsePinDTO savePin(RequestPinDTO data) {
        PinEntity entity = new PinEntity();

        entity.setTitle(data.title());
        entity.setDescription(data.description());
        entity.setImageUrl(data.imageUrl());

        return ResponsePinDTO.fromEntity(entity);
    }

    @Override
    public int likePinById(String id) {
        PinEntity pin = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pin not found with id: " + id));
        
        pin.setLikesCount(pin.getLikesCount() + 1);
        repository.save(pin);
        
        return pin.getLikesCount();
    }
    
}
