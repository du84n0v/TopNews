package top.news.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileRoleEnum;
import top.news.enums.ProfileStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {

    private Integer id;

    private String name;

    private String surname;

    private String username;

    private List<ProfileRoleEnum> roleList;

    private ProfileStatusEnum status;

    private LocalDateTime createdDate;

    private Integer photoId;

    private String jwt;
}
