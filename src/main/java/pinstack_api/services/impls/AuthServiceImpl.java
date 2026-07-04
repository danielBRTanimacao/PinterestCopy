package pinstack_api.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.entities.UserEntity;
import pinstack_api.repositories.UserRepository;
import pinstack_api.services.AuthService;
import pinstack_api.services.auth.TokenService;
import pinstack_api.utils.components.TokenGenerator;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator generator;
    private final TokenService service;

    @Override
    public AuthResponseDTO register(RequestAuthDTO data) {
        log.info("Starting registration for email: {}", data.email());

        if (repository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        UserEntity user = new UserEntity();
        user.setUsername(data.email().substring(0, data.email().indexOf("@")));
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));

        // ===========================================================
        //  ENVIAR O EMAIL NESSA PARTE AQUI AINDA VOU FAZER ESSA GOTA
        // ===========================================================
        user.setVerificationCode(generator.generateToken());
        user.setCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setVerified(false);

        repository.save(user);
        log.info("User successfully registered. Verification code generated.");

        return new AuthResponseDTO(user.getUsername(), user.getEmail(), user.getCodeExpiresAt());
    }

    @Override
    public AuthResponseDTO login(LoginAuthDTO data) {
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

}