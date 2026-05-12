package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Section;

import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Integer> {

    Optional<Section> findByIdAndVisibleTrue(Integer sectionId);

    @Transactional
    @Modifying
    @Query("UPDATE Section s SET s.visible=false WHERE s.id =?1")
    int delete(Integer sectionId);

    Iterable<Section> findAllByVisibleTrue();
}
