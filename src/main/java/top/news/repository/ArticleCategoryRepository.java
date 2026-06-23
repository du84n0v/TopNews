package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleCategoryEntity;

import java.util.List;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    void deleteAllByArticleId(String articleId);

    List<ArticleCategoryEntity> getAllByArticleId(String articleId);
}
