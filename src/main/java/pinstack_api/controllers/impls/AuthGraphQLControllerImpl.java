package pinstack_api.controllers.impls;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.DTOs.auth.ResponseJwtDTO;
import pinstack_api.controllers.AuthGraphQLController;
import pinstack_api.services.AuthService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthGraphQLControllerImpl implements AuthGraphQLController {

    private final AuthService service;

    @Override
    public AuthResponseDTO register(RequestAuthDTO data) {
        return service.register(data);
    }

    @Override
    public ResponseJwtDTO login(LoginAuthDTO data) {
        return service.login(data);
    }
}