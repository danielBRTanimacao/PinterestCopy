package pinstack_api.controllers.impls;

import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.controllers.AuthGraphQLController;


public class AuthGraphQLControllerImpl implements AuthGraphQLController {

    @Override
    public AuthResponseDTO register(RequestAuthDTO data) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public AuthResponseDTO login(LoginAuthDTO data) {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}