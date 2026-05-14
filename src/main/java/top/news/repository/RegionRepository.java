package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import top.news.entity.Region;
import top.news.mapper.RegionMapper;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, Integer> {

    Optional<Region> findByIdAndVisibleTrue(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Region r SET r.visible=false WHERE r.id =?1")
    int delete(Integer regionId);

    Iterable<Region> findAllByVisibleTrue();

    Optional<Region> findByKeyAndVisibleTrue(String key);

    @Query("SELECT r.id AS id, r.key AS key," +
            " CASE :lang " +
            " WHEN 'UZ' THEN r.nameUz " +
            " WHEN 'RU' THEN r.nameRu " +
            " WHEN 'EN' THEN r.nameEn " +
            "END AS name " +
            " FROM Region r " +
            " WHERE r.visible=true ORDER BY r.orderNumber ASC")
    List<RegionMapper> findAllByLang(@Param(("lang")) String lang);
}
