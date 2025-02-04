package app.user.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @Size(min = 5,max = 40,message = "Username must be between 5 and 40 characters!")
    private String username;

    @Size(min = 5,max = 40,message = "Password must be between 5 and 40 characters!")
    private String password;

    @NotNull
    private Country country;
}
