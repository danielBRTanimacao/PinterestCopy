package pinstack_api.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "pins")
public class PinEntity {
    
    @Id
    private String id;

    private String title;
    private String description;
    private String imageUrl;
    private int likesCount = 0;

    @CreatedDate 
    private LocalDateTime createdAt;

    @LastModifiedDate 
    private LocalDateTime updatedAt;
}
