package top.news.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdatePasswordDTO {

    @NotNull
    private String currentPassword;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    private String newPassword;
}
