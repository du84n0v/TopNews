package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Article;
import top.news.enums.ArticleStatusEnum;
import top.news.mapper.ArticleShortInfoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, String> {

    Optional<Article> findByIdAndVisibleTrue(String articleId);

    @Query("SELECT a.id AS id, a.title AS title, a.description AS description, a.imageId AS imageId, a.publishedDate AS publishedDat " +
            " FROM Article a " +
            " INNER JOIN ArticleSection ss ON a.id=ss.articleId " +
            " WHERE ss.sectionId =?1 " +
            " AND a.visible=true " +
            " AND a.status='PUBLISHED'" +
            " ORDER BY a.createdDate DESC ")
    Page<ArticleShortInfoMapper> findNArticleBySectionId(Integer sectionId, Pageable pageable);

    @Query("SELECT a.id AS id, a.title AS title, a.description AS description, a.imageId AS imageId, a.publishedDate AS publishedDat " +
            " FROM Article  a " +
            " WHERE a.id NOT IN ?1 " +
            " AND a.status='PUBLISHED' " +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<ArticleShortInfoMapper> getLast12(List<String> ids, Pageable pageable);

    @Query("SELECT a.id AS id, a.title AS title, a.description AS description, a.imageId AS imageId, a.publishedDate AS publishedDat " +
            " FROM Article a " +
            " INNER JOIN ArticleCategory ac ON a.id=ac.articleId " +
            " WHERE ac.categoryId=?1 " +
            " AND a.status='PUBLISHED' " +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<ArticleShortInfoMapper> findNArticleByCategoryId(Integer categoryId, Pageable pageable);

    @Query("SELECT a.id AS id, a.title AS title, a.description AS description, a.imageId AS imageId, a.publishedDate AS publishedDat " +
            " FROM Article a " +
            " WHERE a.regionId=?1 " +
            " AND a.status='PUBLISHED'" +
            " AND a.visible=true " +
            " ORDER BY a.createdDate DESC")
    Page<ArticleShortInfoMapper> findNArticleByRegionId(Integer regionId, Pageable pageable);

    @Query("SELECT a.id AS id, a.title AS title, a.description AS description, a.imageId AS imageId, a.publishedDate AS publishedDat " +
            " FROM Article a " +
            " WHERE a.id <> ?1 " +
            " AND a.status='PUBLISHED' " +
            " AND a.visible=true " +
            " ORDER BY a.readTime ")
    Page<ArticleShortInfoMapper> find4MostReadExcept(String articleId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Article a " +
            " SET a.viewCount = a.viewCount + 1 " +
            " WHERE a.id = ?1")
    Integer increaseViewCountById(String articleId);

    @Query("SELECT a.viewCount  FROM Article  a WHERE a.id = ?1")
    Integer findViewCountById(String articleId);

    @Transactional
    @Modifying
    @Query("UPDATE Article a " +
            " SET a.sharedCount = a.sharedCount + 1 " +
            " WHERE a.id = ?1")
    int increaseShareCountById(String articleId);

    @Query("SELECT a.sharedCount FROM Article  a WHERE a.id = ?1")
    Integer findShareCountById(String articleId);

    @Transactional
    @Modifying
    @Query("UPDATE Article a SET a.visible=FALSE WHERE a.id = ?1")
    int deleteArticleById(String articleId);

    @Transactional
    @Modifying
    @Query("UPDATE Article a " +
            " SET a.status = ?2, a.publisherId = ?3, " +
            " a.publishedDate = ?4" +
            " WHERE a.id = ?1")
    int changeStatus(String articleId, ArticleStatusEnum status, Integer publisherId, LocalDateTime publishedDate);
}
