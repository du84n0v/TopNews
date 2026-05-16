package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, String> {
}
