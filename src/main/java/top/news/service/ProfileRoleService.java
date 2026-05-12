package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoles;
import top.news.exception.AppBadRequestException;
import top.news.repository.ProfileRoleRepository;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void save(Integer profileId, ProfileRoles role) {
        if(profileRoleRepository.existsByIdAndRole(profileId, role)){
            throw new AppBadRequestException("This role is already assigned to this profile");
        }

        ProfileRole profileRole = new ProfileRole();
        profileRole.setProfileId(profileId);
        profileRole.setRole(role);

        profileRoleRepository.save(profileRole);
    }
}
