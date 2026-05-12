package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category , Integer> {

    Optional<Category> findByIdAndVisibleTrue(Integer categoryId);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.visible=false WHERE c.id =?1")
    int delete(Integer categoryId);
}
