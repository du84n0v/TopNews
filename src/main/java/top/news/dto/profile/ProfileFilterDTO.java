package top.news.dto.profile;

import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileRoleEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String username;
    private List<ProfileRoleEnum> roleList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
