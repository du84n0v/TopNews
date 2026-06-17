package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.SavedArticleEntity;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {
}
