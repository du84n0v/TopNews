package top.news.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentFilterDTO {
    private Integer commentId;
    private LocalDate fromCreatedDate;
    private LocalDate toCreatedDate;
    private Integer profileId;
    private String articleId;
}
