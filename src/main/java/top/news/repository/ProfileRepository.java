package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    @Query("SELECT p FROM ProfileEntity p LEFT JOIN FETCH p.roles WHERE p.username = :username")
    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer profileId);

    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity p SET p.visible=false WHERE p.id =?1")
    int updateVisible(Integer profileId);
}
