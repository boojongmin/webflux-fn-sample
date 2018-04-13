package userserver.handler;

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
    @NonNull private UserService userService;
    private Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(String.class)
                .flatMap(x -> userService.getUser(x))
                .flatMap(x -> ok().body(fromObject(x)) )
                    .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(User.class)
                .flatMap(x -> userService.createUser(x))
                .then(ServerResponse.created(URI.create("/api/user/select")).build());
    }
}
