package top.news.mapper;

import java.time.LocalDateTime;

public interface CommentRepliesMapper {
    Integer getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
    Integer getProfileId();
    String getName();
    String getSurname();
}
