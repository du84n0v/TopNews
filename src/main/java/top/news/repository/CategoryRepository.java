package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.news.entity.Category;
import top.news.mapper.CategoryLangMapper;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category , Integer> {

    Optional<Category> findByIdAndVisibleTrue(Integer categoryId);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.visible=false WHERE c.id =?1")
    int delete(Integer categoryId);

    Iterable<Category> findAllByVisibleTrue();

    Optional<Category> findByKeyAndVisibleTrue(String key);

    @Query("SELECT c.id AS id, c.key AS key, " +
            " CASE :lang " +
            " WHEN 'UZ' THEN c.nameUz " +
            " WHEN 'RU' THEN c.nameRu " +
            " WHEN 'EN' THEN c.nameEn " +
            "END AS name " +
            " FROM Category c " +
            " WHERE c.visible=true ORDER BY c.orderNumber ASC")
    List<CategoryLangMapper> findByLang(@Param("lang") String lang);
}
