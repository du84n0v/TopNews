package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.CommentEntity;
import top.news.entity.CommentLikeEntity;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    Optional<CommentLikeEntity> findByProfileIdAndCommentId(Integer profileId, Integer commentId);
}
