package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleLikeEntity;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);
}
