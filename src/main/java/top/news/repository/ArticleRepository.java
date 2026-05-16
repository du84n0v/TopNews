package top.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, String> {

    Optional<Article> findByIdAndVisibleTrue(String articleId);

    @Query("SELECT a FROM Article a " +
            " INNER JOIN ArticleSection ss ON a.id=ss.articleId " +
            " WHERE ss.sectionId =?1 " +
            " AND a.visible=true " +
            " AND a.status='PUBLISHED'" +
            " ORDER BY a.createdDate DESC ")
    Page<Article> findNArticleBySectionId(Integer sectionId, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            " WHERE a.id NOT IN ?1 " +
            " AND a.status='PUBLISHED' " +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<Article> getLast12(List<String> ids, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            " INNER JOIN ArticleCategory ac ON a.id=ac.articleId " +
            " WHERE ac.categoryId=?1 " +
            " AND a.status='PUBLISHED' " +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<Article> findNArticleByCategoryId(Integer categoryId, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            " WHERE a.regionId=?1 " +
            " AND a.status='PUBLISHED'" +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<Article> findNArticleByRegionId(Integer regionId, Pageable pageable);
}
