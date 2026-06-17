package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.news.entity.CategoryEntity;
import top.news.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByIdAndVisibleTrue(Integer categoryId);

    @Transactional
    @Modifying
    @Query("UPDATE CategoryEntity c SET c.visible=false WHERE c.id =?1")
    int delete(Integer categoryId);

    Iterable<CategoryEntity> findAllByVisibleTrue();

    Optional<CategoryEntity> findByKeyAndVisibleTrue(String key);

    @Query("SELECT c.id AS id, c.key AS key, " +
            " CASE :lang " +
            " WHEN 'UZ' THEN c.nameUz " +
            " WHEN 'RU' THEN c.nameRu " +
            " WHEN 'EN' THEN c.nameEn " +
            "END AS name " +
            " FROM CategoryEntity c " +
            " WHERE c.visible=true ORDER BY c.orderNumber ASC")
    List<CategoryMapper> findByLang(@Param("lang") String lang);
}
