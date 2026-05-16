package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleCategory;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategory, Integer> {

    void deleteAllByArticleId(String articleId);
}
