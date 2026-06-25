package pinstack_api.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "pins")
public class Pin {
    
    @Id
    private Long id;

    private String title;
    private String description;
    private String imageUrl;
    private int likesCount = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
}
