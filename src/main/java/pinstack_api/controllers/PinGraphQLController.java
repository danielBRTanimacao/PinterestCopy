package pinstack_api.controllers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import jakarta.validation.Valid;
import pinstack_api.DTOs.RequestPinDTO;
import pinstack_api.DTOs.ResponsePinDTO;

import java.util.List;

public interface PinGraphQLController {

    @QueryMapping
    List<ResponsePinDTO> mainFeed();

    @QueryMapping
    ResponsePinDTO pinById(@Argument String id);

    @MutationMapping
    ResponsePinDTO createPin(@Argument @Valid RequestPinDTO data);

    @MutationMapping
    int likePin(@Argument String id);
}