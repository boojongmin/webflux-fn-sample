package userserver.configuration;

import lombok.extern.slf4j.Slf4j;
import userserver.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class RoutingConfiguration {
    RequestPredicate accept = accept(MediaType.APPLICATION_JSON);

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(POST("/api/user/select").and(accept), userHandler::getUser)
                .andRoute(POST("/api/user/create").and(accept), userHandler::createUser)
                .filter((request, next) -> {
                    log.debug("do something!!");
                    return next.handle(request);
                });
    }
}
