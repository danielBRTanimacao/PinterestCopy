package pinstack_api.controllers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import pinstack_api.entities.PinEntity;

import java.util.List;

public interface PinGraphQLController {

    @QueryMapping
    List<PinEntity> mainFeed();

    @QueryMapping
    PinEntity pinById(@Argument String id);

    @MutationMapping
    PinEntity createPin(@Argument String title, @Argument String description, @Argument String imageUrl);

    @MutationMapping
    int likePin(@Argument String id);
}