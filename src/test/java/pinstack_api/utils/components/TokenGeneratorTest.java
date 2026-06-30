package pinstack_api.utils.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenGeneratorTest {

    private TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        this.tokenGenerator = new TokenGenerator(); 
    }

    @Test
    @DisplayName("Should raise error if the TOKEN is bigger than allowed")
    void raiseErrorIfTokenIsBiggerThanAllowed() {
        
        String token = tokenGenerator.generateToken();
        assertTrue(token.length() <= 5, "The token length should not exceed 5 characters");
    }

    @Test
    @DisplayName("Should create a TOKEN with aleatories values enter 0 and 10 and using alphabet characters")
    void createToken_success() {
        String token = tokenGenerator.generateToken();

        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        assertEquals(5, token.length(), "The token length should be 5");
        
        assertTrue(token.matches("^[a-zA-Z0-9]+$"), "The token should contain only alphanumeric characters");
    }
}
