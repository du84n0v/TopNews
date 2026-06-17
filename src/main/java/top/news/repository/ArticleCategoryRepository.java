package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleCategoryEntity;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    void deleteAllByArticleId(String articleId);
}
