package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoles;

public interface ProfileRoleRepository extends CrudRepository<ProfileRole, Integer> {

    boolean existsByIdAndRole(Integer profileId, ProfileRoles profileRole);
}
