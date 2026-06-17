package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ProfileRoleEntity;
import top.news.enums.ProfileRoleEnum;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    boolean existsByProfileIdAndRole(Integer profileId, ProfileRoleEnum role);

    List<ProfileRoleEntity> findAllByProfileId(Integer profileId);

    void deleteByProfileId(Integer profileId);
}
