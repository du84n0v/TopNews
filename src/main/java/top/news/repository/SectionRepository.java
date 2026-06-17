package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.news.entity.SectionEntity;
import top.news.mapper.SectionMapper;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {

    Optional<SectionEntity> findByIdAndVisibleTrue(Integer sectionId);

    @Transactional
    @Modifying
    @Query("UPDATE SectionEntity s SET s.visible=false WHERE s.id =?1")
    int delete(Integer sectionId);

    Page<SectionEntity> findAllByVisibleTrue(Pageable pageable);

    Optional<SectionEntity> findByKeyAndVisibleTrue(String key);

    @Query("SELECT s.id AS id, s.key AS key, " +
            " CASE :lang " +
            "   WHEN 'UZ' THEN s.nameUz " +
            "   WHEN 'RU' THEN s.nameRu " +
            "   WHEN 'EN' THEN s.nameEn " +
            " END AS name " +
            " FROM SectionEntity s " +
            " WHERE s.visible=true ORDER BY s.orderNumber ASC")
    List<SectionMapper> getByLang(@Param("lang") String lang);
}
