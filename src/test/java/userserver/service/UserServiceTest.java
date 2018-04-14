package userserver.service;

import userserver.domain.User;
import userserver.model.TestModelFactory;
import userserver.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserServiceTest {
    @Autowired UserRepository userRepository;
    UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository);
        userRepository.deleteAll().block();
    }

    @Test
    public void testService() {
        User user = TestModelFactory.createUser();
        userService.createUser(user).block();
        User result = userService.getUser(user.getUserid()).block();
        assertThat(result).isEqualTo(user);
    }
}
