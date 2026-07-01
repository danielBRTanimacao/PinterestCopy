package pinstack_api.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;

    private String verificationCode;
    private boolean isVerified = false;
    private int verificationAttempts = 0;
    
    private LocalDateTime codeExpiresAt;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate 
    private LocalDateTime updatedAt;

    public boolean isVerificationCodeExpired() {
        if (this.codeExpiresAt == null) return true;
        return LocalDateTime.now().isAfter(this.codeExpiresAt);
    }
}
