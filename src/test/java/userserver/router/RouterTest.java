package userserver.router;

import userserver.configuration.RoutingConfiguration;
import userserver.domain.User;
import userserver.handler.UserHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RunWith(SpringRunner.class)
@Import(RoutingConfiguration.class)
public class RouterTest {
    @Autowired RouterFunction router;
    @MockBean UserHandler handler;
    private WebTestClient testClient;

    @Before
    public void setUp() {
        this.testClient = WebTestClient.bindToRouterFunction(router).build();
    }

    @Test
    public void createUser() {
        User user = User.getTestUser();
        given(handler.createUser(any())).willReturn(created(null).build());
        this.testClient
                .post()
                .uri("/api/user/create")
                .body(fromObject(user))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void getUser() {
        given(handler.getUser(any())).willReturn(ok().build());
        this.testClient
                .post()
                .uri("/api/user/select")
                .body(fromObject("000000-000000"))
                .exchange()
                .expectStatus().isOk();
    }
}

