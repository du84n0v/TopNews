package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.news.entity.Section;
import top.news.mapper.SectionMapper;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Integer> {

    Optional<Section> findByIdAndVisibleTrue(Integer sectionId);

    @Transactional
    @Modifying
    @Query("UPDATE Section s SET s.visible=false WHERE s.id =?1")
    int delete(Integer sectionId);

    Iterable<Section> findAllByVisibleTrue();

    Optional<Section> findByKeyAndVisibleTrue(String key);

    @Query("SELECT s.id AS id, s.key AS key, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN s.nameUz " +
            "   WHEN 'RU' THEN s.nameRu " +
            "   WHEN 'EN' THEN s.nameEn " +
            " END AS name " +
            " FROM Section s " +
            " WHERE s.visible=true ORDER BY s.orderNumber ASC")
    List<SectionMapper> getByLang(@Param("lang") String lang);
}
