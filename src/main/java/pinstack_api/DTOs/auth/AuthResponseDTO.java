package pinstack_api.DTOs.auth;

import java.time.LocalDateTime;

public record AuthResponseDTO(
    String username, 
    String email, 
    LocalDateTime codeExpiresAt
) {
    
}
