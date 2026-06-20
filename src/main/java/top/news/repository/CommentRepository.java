package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.CommentEntity;
import top.news.mapper.CommentArticleMapper;
import top.news.mapper.CommentRepliesMapper;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    Optional<CommentEntity> findByIdAndVisibleTrue(Integer commentId);

    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity ce SET ce.visible=false WHERE ce.id = ?1")
    int changeVisibleById(Integer commentId);

    @Query("SELECT ce.id AS id, ce.createdDate AS createdDate, " +
            " ce.updateDate AS updatedDate, pe.id AS profileId, " +
            " pe.name AS name, pe.surname AS surname" +
            " FROM CommentEntity  ce " +
            " INNER JOIN ProfileEntity pe on pe.id = ce.profileId " +
            " WHERE ce.visible=true AND ce.replyId = ?1")
    List<CommentRepliesMapper> getReplies(Integer commentId);

    @Query("SELECT c.id AS id, c.createdDate AS createdDate, c.updateDate AS updateDate, " +
            " c.content AS content, c.articleId AS articleId, " +
            " p.id AS profileId, p.name AS profileName, " +
            " p.photoId AS photoId, " + // Rasm UUID ID-si
            " (SELECT COUNT(cl) FROM CommentLikeEntity cl WHERE cl.commentId = c.id AND cl.status = 'LIKE') AS likeCount, " +
            " (SELECT COUNT(cl) FROM CommentLikeEntity cl WHERE cl.commentId = c.id AND cl.status = 'DISLIKE') AS dislikeCount " +
            " FROM CommentEntity c " +
            " INNER JOIN ProfileEntity p on p.id = c.profileId " +
            " WHERE c.articleId = ?1 AND c.visible = true")
    List<CommentArticleMapper> getArticleComments(String articleId);
}
