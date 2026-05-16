package top.news.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoleEnum;
import top.news.exception.AppBadRequestException;
import top.news.repository.ProfileRoleRepository;

import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void save(Integer profileId, ProfileRoleEnum role) {
        if(profileRoleRepository.existsByProfileIdAndRole(profileId, role)){
            throw new AppBadRequestException("This role is already assigned to this profile");
        }

        ProfileRole profileRole = new ProfileRole();
        profileRole.setProfileId(profileId);
        profileRole.setRole(role);

        profileRoleRepository.save(profileRole);
    }

    public List<ProfileRoleEnum> getProfileRolesById(Integer profileId) {
        return profileRoleRepository.findAllByProfileId(profileId).stream()
                .map(ProfileRole::getRole)
                .toList();

    }

    @Transactional
    public void merge(Integer profileId, List<ProfileRoleEnum> roleList) {
        deleteProfileRoles(profileId);

        List<ProfileRole> roles = roleList.stream()
                .map(role -> {
                    ProfileRole profileRole = new ProfileRole();
                    profileRole.setProfileId(profileId);
                    profileRole.setRole(role);
                    return profileRole;
                }).toList();

        profileRoleRepository.saveAll(roles);
    }

    private void deleteProfileRoles(Integer profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }
}
