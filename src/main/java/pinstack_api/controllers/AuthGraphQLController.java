package pinstack_api.controllers;

import org.springframework.graphql.data.method.annotation.Argument;

import jakarta.validation.Valid;
import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.DTOs.auth.ResponseJwtDTO;

public interface AuthGraphQLController {
    AuthResponseDTO register(@Argument @Valid RequestAuthDTO data);
    ResponseJwtDTO login(@Argument @Valid LoginAuthDTO data);
}
