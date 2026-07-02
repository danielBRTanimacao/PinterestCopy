package pinstack_api.services;

import pinstack_api.DTOs.auth.*;

public interface AuthService {
    AuthResponseDTO register(RequestAuthDTO data);
    AuthResponseDTO login(LoginAuthDTO data);
}
