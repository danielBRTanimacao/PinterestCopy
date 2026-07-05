package pinstack_api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import pinstack_api.DTOs.auth.AuthResponseDTO;
import pinstack_api.DTOs.auth.LoginAuthDTO;
import pinstack_api.DTOs.auth.RequestAuthDTO;
import pinstack_api.DTOs.auth.ResponseJwtDTO;
import pinstack_api.entities.UserEntity;
import pinstack_api.exceptions.raises.NotFoundException;
import pinstack_api.exceptions.raises.PermissionDeniedException;
import pinstack_api.repositories.UserRepository;
import pinstack_api.services.auth.TokenService;
import pinstack_api.services.impls.AuthServiceImpl;
import pinstack_api.utils.components.TokenGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenGenerator generator;

    @Mock
    private TokenService tokenService;

    @Test
    @DisplayName("Should register a new user successfully")
    void shouldRegisterUserSuccessfully() {
        RequestAuthDTO request = new RequestAuthDTO("teste@pinstack.com", "senha123");
        
        when(repository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("senha-hasheada");
        when(generator.generateToken()).thenReturn("123456");

        AuthResponseDTO response = authService.register(request);

        assertNotNull(response);
        assertEquals("teste", response.username());
        assertEquals("teste@pinstack.com", response.email());
        assertNotNull(response.codeExpiresAt());

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(repository, times(1)).save(userCaptor.capture());
        
        UserEntity savedUser = userCaptor.getValue();
        assertEquals("teste", savedUser.getUsername());
        assertEquals("teste@pinstack.com", savedUser.getEmail());
        assertEquals("senha-hasheada", savedUser.getPassword());
        assertEquals("123456", savedUser.getVerificationCode());
        assertFalse(savedUser.isVerified());
    }

    @Test
    @DisplayName("Should throw exception if email is already registered")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RequestAuthDTO request = new RequestAuthDTO("teste@pinstack.com", "senha123");
        when(repository.findByEmail(request.email())).thenReturn(Optional.of(new UserEntity()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });
        assertEquals("Email already registered", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should log in successfully and return the JWT token")
    void shouldLoginSuccessfully() {
        LoginAuthDTO request = new LoginAuthDTO("teste@pinstack.com", "senha123");
        
        UserEntity userMock = new UserEntity();
        userMock.setUsername("teste");
        userMock.setPassword("senha-hasheada");
        userMock.setVerified(true); // O usuário DEVE estar verificado para passar

        when(repository.findByUsernameOrEmail(request.nameOrEmail())).thenReturn(Optional.of(userMock));
        when(passwordEncoder.matches(request.password(), userMock.getPassword())).thenReturn(true);
        when(tokenService.generateToken(userMock)).thenReturn("ey.jwt.token");

        ResponseJwtDTO response = authService.login(request);

        assertNotNull(response);
        assertEquals("ey.jwt.token", response.token());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        LoginAuthDTO request = new LoginAuthDTO("fantasma@pinstack.com", "senha123");
        when(repository.findByUsernameOrEmail(request.nameOrEmail())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            authService.login(request);
        });
        assertEquals("Invalid username/email or password", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw PermissionDeniedException when user is not verified")
    void shouldThrowPermissionDeniedWhenUserNotVerified() {
        LoginAuthDTO request = new LoginAuthDTO("teste@pinstack.com", "senha123");
        
        UserEntity userMock = new UserEntity();
        userMock.setVerified(false); // Usuário não verificou o e-mail

        when(repository.findByUsernameOrEmail(request.nameOrEmail())).thenReturn(Optional.of(userMock));

        PermissionDeniedException exception = assertThrows(PermissionDeniedException.class, () -> {
            authService.login(request);
        });
        assertEquals("Please verify your account before logging in.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw PermissionDeniedException when the password is wrong")
    void shouldThrowPermissionDeniedWhenPasswordIsWrong() {
        LoginAuthDTO request = new LoginAuthDTO("teste@pinstack.com", "senha_errada");
        
        UserEntity userMock = new UserEntity();
        userMock.setVerified(true);
        userMock.setPassword("senha-hasheada");

        when(repository.findByUsernameOrEmail(request.nameOrEmail())).thenReturn(Optional.of(userMock));
        when(passwordEncoder.matches(request.password(), userMock.getPassword())).thenReturn(false);

        PermissionDeniedException exception = assertThrows(PermissionDeniedException.class, () -> {
            authService.login(request);
        });
        assertEquals("Invalid username/email or password", exception.getMessage());
    }
}