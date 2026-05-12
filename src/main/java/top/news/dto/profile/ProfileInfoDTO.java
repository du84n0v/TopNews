package top.news.dto.profile;

import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileRoles;
import top.news.enums.ProfileStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private List<ProfileRoles> roleList;
    private ProfileStatus status;
    private LocalDateTime createdDate;
    private Integer photoId;
}
