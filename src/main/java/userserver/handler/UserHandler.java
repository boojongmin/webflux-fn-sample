package userserver.handler;

import userserver.handler.validator.ModelValidator;
import userserver.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import userserver.domain.User;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class UserHandler {
    @NonNull private final UserService userService;
    @NonNull private final ModelValidator validator;

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return serverRequest
                .bodyToMono(String.class)
                .flatMap(x -> userService.getUser(x))
                .flatMap(x -> ok().body(fromObject(x)) )
                    .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(User.class)
                .flatMap(validator::validate)
                .flatMap(userService::createUser)
                .then(ServerResponse.created(URI.create("/api/user/select")).build());
    }
}
