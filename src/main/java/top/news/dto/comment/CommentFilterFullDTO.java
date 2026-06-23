package top.news.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.news.dto.article.ArticleShortDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentFilterFullDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String content;
    private ArticleShortDTO articleDto;
    private CommentProfileDTO profileDto;
    private Integer replyId;
    private Boolean visible;
    private Integer likeCount;
}
