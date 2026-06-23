package top.news.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentArticleDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String content;
    private String articleId;
    private CommentProfileDTO profileDto;
    private Integer likeCount;
}
