package userserver.service;

import userserver.domain.User;
import userserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public Mono<User> getUser(String userid) {
        return userRepository.findByUserid(userid);
    }

    public Mono<User> createUser(User user) {
        return getUser(user.getUserid())
                .map(x -> {
                    user.setId(x.getId());
                    return user;
                })
                .flatMap(x -> userRepository.save(x))
                .switchIfEmpty(userRepository.save(user));
    }

}
