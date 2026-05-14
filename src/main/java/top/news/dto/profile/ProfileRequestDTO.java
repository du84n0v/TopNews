package top.news.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileRoleEnum;
import top.news.enums.ProfileStatusEnum;

import java.util.List;

@Getter
@Setter
public class ProfileRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Username is required")
    private String username;

    @NotEmpty(message = "Role is required")
    private List<ProfileRoleEnum> roleList;

    @NotNull(message = "Status is required")
    private ProfileStatusEnum status;
}
