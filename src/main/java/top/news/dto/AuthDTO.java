package top.news.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NotBlank(message = "Surname should not be empty")
    private String surname;
    @NotBlank(message = "Username should not be empty")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String username;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    private String password;
}
