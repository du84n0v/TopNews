package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleSectionEntity;

import java.util.List;

public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {

    void deleteAllByArticleId(String articleId);

    List<ArticleSectionEntity> findAllByArticleId(String articleId);
}
