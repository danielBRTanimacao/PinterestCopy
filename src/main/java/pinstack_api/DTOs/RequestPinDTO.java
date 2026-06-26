package pinstack_api.DTOs;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pinstack_api.entities.PinEntity;

public record RequestPinDTO(
    @NotBlank
    @Size(min = 3, max = 50, message = "The title must be between 3 and 50 characters long.")
    String title,
    
    @Size(max = 500, message = "The description cannot exceed 500 characters.")
    String description, 

    @NotBlank
    @URL(message = "The image URL must be a valid link.")
    String imageUrl
) {
    public static PinEntity toSaveEntity(RequestPinDTO dto) {
        PinEntity pin = new PinEntity();
        pin.setTitle(dto.title());
        pin.setDescription(dto.description());
        pin.setImageUrl(dto.imageUrl());
        return pin;
    }
}
