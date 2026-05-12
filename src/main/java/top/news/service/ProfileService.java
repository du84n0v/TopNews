package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.ProfileDTO;
import top.news.dto.ProfileInfoDTO;
import top.news.entity.Profile;
import top.news.enums.ProfileRoles;
import top.news.repository.ProfileRepository;

import java.time.LocalDateTime;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;

    public ProfileInfoDTO save(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(dto.getPassword());
        profile.setStatus(dto.getStatus());
        profile.setVisible(true);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        for (ProfileRoles profileRoles : dto.getRoleList()) {
            profileRoleService.save(profile.getId(), profileRoles);
        }

        ProfileInfoDTO response = new ProfileInfoDTO();
        response.setId(profile.getId());
        response.setName(profile.getName());
        response.setSurname(profile.getSurname());
        response.setUsername(profile.getUsername());
        response.setRoleList(dto.getRoleList());
        response.setStatus(profile.getStatus());
        response.setCreatedDate(profile.getCreatedDate());
        response.setPhotoId(profile.getPhotoId());
        return response;
    }
}
