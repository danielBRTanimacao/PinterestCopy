package pinstack_api.DTOs;

import java.time.LocalDateTime;

import pinstack_api.entities.PinEntity;

public record ResponsePinDTO(
    String id,
    String title,
    String description,
    String imageUrl,
    int likesCount,
    LocalDateTime createdAt
) {
    public static ResponsePinDTO fromEntity(PinEntity pin) {
        return new ResponsePinDTO(
            pin.getId(),
            pin.getTitle(),
            pin.getDescription(),
            pin.getImageUrl(),
            pin.getLikesCount(),
            pin.getCreatedAt()
        );
    }
}
