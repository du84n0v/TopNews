package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ArticleSection;

public interface ArticleSectionRepository extends CrudRepository<ArticleSection, Integer> {
}
