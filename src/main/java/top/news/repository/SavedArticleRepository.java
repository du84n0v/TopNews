package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.SavedArticle;

public interface SavedArticleRepository extends CrudRepository<SavedArticle, Integer> {
}
