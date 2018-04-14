package userserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {
    @JsonIgnore @Id private String id;
    @NonNull @NotBlank @Size(min = 5, max = 20) private String userid;
    @NonNull @NotBlank @Size(min = 5, max = 100) private String password;
    @NonNull @NotBlank private String name;
}
