package userserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {
    @Id private String id;
    @NonNull private String userid;
    @NonNull private String password;
    @NonNull private String name;

    public static User getTestUser() {
        return new User("000000-0000000", "1111111111", "김말자");
    }
}
