package userserver.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import userserver.domain.User;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByUserid(String userid);
}
