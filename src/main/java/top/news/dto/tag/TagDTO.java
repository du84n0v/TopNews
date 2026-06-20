package top.news.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Size should be between 2 and 30")
    private String name;
}
