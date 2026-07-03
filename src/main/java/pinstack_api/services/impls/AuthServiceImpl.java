package pinstack_api.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.entities.UserEntity;
import pinstack_api.repositories.UserRepository;
import pinstack_api.services.AuthService;
import pinstack_api.services.auth.TokenService;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService service;

    @Override
    public AuthResponseDTO register(RequestAuthDTO data) {
        UserEntity user = new UserEntity();
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));
        repository.save(user);
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }
    @Override
    public AuthResponseDTO login(LoginAuthDTO data) {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

}