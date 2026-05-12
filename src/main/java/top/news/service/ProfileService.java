package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.news.dto.ProfileDTO;
import top.news.dto.ProfileFilterDTO;
import top.news.dto.ProfileInfoDTO;
import top.news.dto.ProfileShortUpdateDTO;
import top.news.entity.Profile;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoles;
import top.news.exception.ItemNotFoundException;
import top.news.repository.ProfileRepository;
import top.news.repository.custom.ProfileCustomRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

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

        return entityToInfoDTO(profile, dto.getRoleList());
    }

    public ProfileInfoDTO entityToInfoDTO(Profile profile, List<ProfileRoles> roles){
        ProfileInfoDTO response = new ProfileInfoDTO();
        response.setId(profile.getId());
        response.setName(profile.getName());
        response.setSurname(profile.getSurname());
        response.setUsername(profile.getUsername());
        response.setRoleList(roles);
        response.setStatus(profile.getStatus());
        response.setCreatedDate(profile.getCreatedDate());
        response.setPhotoId(profile.getPhotoId());

        return response;
    }

    public ProfileDTO getById(Integer profileId) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        Profile profile = optional.get();
        ProfileDTO response = new ProfileDTO();
        response.setName(profile.getName());
        response.setSurname(profile.getSurname());
        response.setUsername(profile.getUsername());
        response.setPassword(profile.getPassword());
        response.setStatus(profile.getStatus());
        response.setRoleList(new LinkedList<>(profileRoleService.getProfileRolesById(profileId)));

        return response;
    }

    public String updateProfileById(Integer profileId, ProfileShortUpdateDTO dto) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile is not found");
        }
        Profile profile = optional.get();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());

        profileRepository.save(profile);

        return "Successfully updated";
    }

    public PageImpl<ProfileInfoDTO> getProfileList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Profile> pages = profileRepository.findAllByVisibleTrue(pageRequest);

        List<ProfileInfoDTO> response = pages.stream()
                .map(p -> entityToInfoDTO(p, profileRoleService.getProfileRolesById(p.getId())))
                .toList();
        return new PageImpl<>(response, pageRequest, pages.getTotalElements());
    }

    public PageImpl<ProfileInfoDTO> studentFilter(ProfileFilterDTO dto, Integer page, Integer size) {
        PageImpl<Profile> profiles = profileCustomRepository.filter(dto, page, size);


        List<ProfileInfoDTO> response = profiles.stream()
                .map(profile -> entityToInfoDTO(profile, profileRoleService.getProfileRolesById(profile.getId())))
                .toList();
        return new PageImpl<>(response, PageRequest.of(page, size), profiles.getTotalElements());
    }
}
