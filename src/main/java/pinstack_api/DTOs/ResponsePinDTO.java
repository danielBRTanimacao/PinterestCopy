package pinstack_api.DTOs;

import java.time.LocalDateTime;

public record ResponsePinDTO(
    String id,
    String title,
    String description,
    String imageUrl,
    int likesCount,
    LocalDateTime createdAt
) {}
