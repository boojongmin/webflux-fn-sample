package userserver.integration;

import userserver.domain.User;
import userserver.model.TestModelFactory;
import userserver.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
    @Autowired WebTestClient client;
    @Autowired UserRepository userRepository;
    @LocalServerPort private int port;

    @Before
    public void setup() {
        userRepository.deleteAll().block();

        this.client = WebTestClient
                .bindToServer()
                .responseTimeout(Duration.ofMillis(Long.MAX_VALUE))
                .baseUrl("http://localhost:" + this.port)
                .build();
    }

    @Test
    public void getUser() {
        User input = TestModelFactory.createUser();

        userRepository.save(input).block();
        this.client
                .post()
                .uri("/api/user/select")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(input.getUserid()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .consumeWith(x -> {
                    User output = x.getResponseBody();
                    assertThat(output.getId()).isEqualTo(null);
                    assertThat(output.getUserid()).isEqualTo(input.getUserid());
                    assertThat(output.getPassword()).isEqualTo(input.getPassword());
                    assertThat(output.getName()).isEqualTo(input.getName());
                });
    }

    @Test
    public void createUser() {
        User user = TestModelFactory.createUser();
        this.client
                .post()
                .uri("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(user))
                .exchange()
                .expectStatus().isCreated();
        StepVerifier.create(userRepository.findAll())
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void createUser_invalid_request_body_then_return_status_code_422() {
        User user = TestModelFactory.createUser();
        // set invalid model
        user.setUserid("");
        this.client
                .post()
                .uri("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(user))
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}