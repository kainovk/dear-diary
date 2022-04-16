package dev.kainov.deardiary.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class UserRequest {

    @NotBlank(message = "name should not be blank")
    private String name;
    @NotBlank(message = "email should not be blank")
    @Email(message = "wrong email pattern")
    private String email;
    @Past(message = "date should be past")
    @NotNull(message = "date of birth should not be null")
    private LocalDate birthday;
    private String status;
}
