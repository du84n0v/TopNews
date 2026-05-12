package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Region;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, Integer> {

    Optional<Region> findByIdAndVisibleTrue(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Region r SET r.visible=false WHERE r.id =?1")
    int delete(Integer regionId);

    Iterable<Region> findAllByVisibleTrue();
}
