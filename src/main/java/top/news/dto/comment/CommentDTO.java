package top.news.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    @NotBlank
    private String content;

    @NotNull
    private String articleId;

    private Integer replyId;
}
