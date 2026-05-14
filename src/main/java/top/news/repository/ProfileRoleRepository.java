package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoleEnum;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRole, Integer> {

    boolean existsByProfileIdAndRole(Integer profileId, ProfileRoleEnum role);

    List<ProfileRole> findAllByProfileId(Integer profileId);

    void deleteByProfileId(Integer profileId);
}
