package top.news.mapper;

import java.time.LocalDateTime;

public interface CommentArticleMapper {
    Integer getId();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
    Integer getProfileId();
    String getName();
    String getPhotoId();
    String getContent();
    String getArticleId();
    Integer getLikeCount();
    Integer getDislikeCount();

}
