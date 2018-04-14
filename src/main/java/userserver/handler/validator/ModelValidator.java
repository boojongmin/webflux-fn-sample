package userserver.handler.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import userserver.exception.UnprocessableEntityException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.springframework.web.reactive.function.server.ServerResponse.unprocessableEntity;

@AllArgsConstructor
@Component
public class ModelValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T model) {
        return Mono.just(model).handle((x, sink) -> {
            Set<ConstraintViolation<T>> set = validator.validate(model);
            if(set.isEmpty()) {
                sink.next(x);
            } else {
                sink.error(new UnprocessableEntityException("unprocessable entify"));
            }
        });
    }

}
