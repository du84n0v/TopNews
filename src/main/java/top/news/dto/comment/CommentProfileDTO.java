package top.news.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import top.news.dto.attach.AttachShortInfoDTO;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentProfileDTO {
    private Integer profileId;
    private String name;
    private String surname;
    private AttachShortInfoDTO imageDto;
}
