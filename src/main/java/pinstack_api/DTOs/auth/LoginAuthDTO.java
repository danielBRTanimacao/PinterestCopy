package pinstack_api.DTOs.auth;

public record LoginAuthDTO(
    String nameOrEmail,
    String password
) {
    
}
