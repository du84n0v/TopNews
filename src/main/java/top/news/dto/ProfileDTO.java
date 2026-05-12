package top.news.dto;

import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileRoles;
import top.news.enums.ProfileStatus;

import java.util.List;

@Getter
@Setter
public class ProfileDTO {
    private String name;
    private String surname;
    private String username;
    private String password;
    private List<ProfileRoles> roleList;
    private ProfileStatus status;
}
