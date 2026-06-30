package pinstack_api.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;
    
    private String username;
    private String email;
    private String password;

    private String verificationCode;
    private boolean isVerified;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate 
    private LocalDateTime updatedAt;

    protected boolean isVerificationCodeExpired() {
        if (this.createdAt == null) return true;
        return LocalDateTime.now().isAfter(this.createdAt.plusMinutes(15L));
    }
}
