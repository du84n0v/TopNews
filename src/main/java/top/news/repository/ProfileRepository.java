package top.news.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {

    Optional<Profile> findByUsernameAndVisibleTrue(String username);

    Optional<Profile> findByIdAndVisibleTrue(Integer profileId);

    Page<Profile> findAllByVisibleTrue(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Profile p SET p.visible=false WHERE p.id =?1")
    int updateVisible(Integer profileId);
}
