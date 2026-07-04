package pinstack_api.controllers.impls;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
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
    @MutationMapping
    public AuthResponseDTO register(@Argument @Valid RequestAuthDTO data) {
        log.info("GraphQL Mutation: register requested for email: {}", data.email());
        return service.register(data);
    }

    @Override
    @MutationMapping
    public ResponseJwtDTO login(@Argument @Valid LoginAuthDTO data) {
        log.info("GraphQL Mutation: login requested for username: {}", data.nameOrEmail());
        return service.login(data);
    }
}