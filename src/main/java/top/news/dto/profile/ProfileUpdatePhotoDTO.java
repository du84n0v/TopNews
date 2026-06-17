package top.news.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdatePhotoDTO {
    @NotBlank
    private String photoId;
}
