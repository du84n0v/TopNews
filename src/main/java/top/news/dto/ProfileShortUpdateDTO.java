package top.news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileShortUpdateDTO {
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NotBlank(message = "Surname should not be empty")
    private String surname;
}
