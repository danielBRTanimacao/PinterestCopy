package pinstack_api.controllers;

import org.springframework.graphql.data.method.annotation.Argument;

import jakarta.validation.Valid;
import pinstack_api.DTOs.RequestPinDTO;
import pinstack_api.DTOs.ResponsePinDTO;

import java.util.List;

public interface PinGraphQLController {
    List<ResponsePinDTO> mainFeed();
    ResponsePinDTO pinById(@Argument String id);
    ResponsePinDTO createPin(@Argument @Valid RequestPinDTO data);
    int likePin(@Argument String id);
}