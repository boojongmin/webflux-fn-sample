package userserver.handler;

import userserver.domain.User;
import userserver.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
public class UserHandlerTest {
    UserHandler handler;
    @MockBean UserService userService;

    @Before
    public void setup() {
        this.handler = new UserHandler(userService);
    }

    @Test
    public void getUser() {
        User user = User.getTestUser();
        MockServerRequest request = MockServerRequest.builder().body(Mono.just(user.getUserid()));
        given(userService.getUser(user.getUserid())).willReturn(Mono.just(user));
        Mono<ServerResponse> response = handler.getUser(request);
        StepVerifier.create(response)
                .consumeNextWith(x -> {
                    assertThat(x.statusCode()).isEqualTo(HttpStatus.OK);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void createUser() {
        User user = User.getTestUser();
        MockServerRequest request = MockServerRequest.builder().body(Mono.just(user));
        given(userService.createUser(user)).willReturn(Mono.empty());
        Mono<ServerResponse> response = handler.createUser(request);
        StepVerifier.create(response)
                .consumeNextWith(x -> {
                    assertThat(x.statusCode()).isEqualTo(HttpStatus.CREATED);
                })
                .expectComplete()
                .verify();
    }
}