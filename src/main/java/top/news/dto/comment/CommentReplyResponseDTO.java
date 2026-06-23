package top.news.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentReplyResponseDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private CommentProfileDTO profileDto;
    private Integer likeCount;
}
