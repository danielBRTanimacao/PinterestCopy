package pinstack_api.controllers;

import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;

public interface AuthGraphQLController {
    AuthResponseDTO register(RequestAuthDTO data);
    AuthResponseDTO login(LoginAuthDTO data);
}
