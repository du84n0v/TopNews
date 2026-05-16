package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import top.news.dto.profile.*;
import top.news.entity.Profile;
import top.news.enums.ProfileRoleEnum;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.repository.ProfileRepository;
import top.news.repository.custom.ProfileCustomRepository;
import top.news.util.MD5Encode;

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

    public ProfileResponseDTO save(ProfileRequestDTO dto) {
        Optional<Profile> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if(optional.isPresent()){
            throw new AppBadRequestException("User exists");
        }
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setStatus(dto.getStatus());
        profile.setVisible(Boolean.TRUE);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        profileRoleService.merge(profile.getId(), dto.getRoleList());

        return toResponseDTO(profile, dto.getRoleList());
    }

    public ProfileResponseDTO toResponseDTO(Profile profile, List<ProfileRoleEnum> roles){
        ProfileResponseDTO response = new ProfileResponseDTO();
        if(profile.getId() != null) response.setId(profile.getId());
        if(profile.getName() != null) response.setName(profile.getName());
        if(profile.getSurname() != null) response.setSurname(profile.getSurname());
        if(profile.getUsername() != null) response.setUsername(profile.getUsername());
        if(profile.getRoles() != null && !profile.getRoles().isEmpty()) response.setRoleList(roles);
        if(profile.getStatus() != null) response.setStatus(profile.getStatus());
        if(profile.getCreatedDate() != null) response.setCreatedDate(profile.getCreatedDate());
        if(profile.getPhotoId() != null) response.setPhotoId(profile.getPhotoId());

        return response;
    }

    public ProfileResponseDTO getById(Integer profileId) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        Profile profile = optional.get();
        ProfileResponseDTO response = new ProfileResponseDTO();
        response.setName(profile.getName());
        response.setSurname(profile.getSurname());
        response.setUsername(profile.getUsername());
        response.setStatus(profile.getStatus());
        response.setRoleList(new LinkedList<>(profileRoleService.getProfileRolesById(profileId)));

        return response;
    }

    public String updateProfileById(Integer profileId, ProfileDetailUpdateDTO dto) {
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

    public PageImpl<ProfileResponseDTO> getProfileList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Profile> pages = profileRepository.findAllByVisibleTrue(pageRequest);

        List<ProfileResponseDTO> response = pages.stream()
                .map(p -> toResponseDTO(p, profileRoleService.getProfileRolesById(p.getId())))
                .toList();
        return new PageImpl<>(response, pageRequest, pages.getTotalElements());
    }

    public PageImpl<ProfileResponseDTO> studentFilter(ProfileFilterDTO dto, Integer page, Integer size) {
        PageImpl<Profile> profiles = profileCustomRepository.filter(dto, page, size);


        List<ProfileResponseDTO> response = profiles.stream()
                .map(profile -> toResponseDTO(profile, profileRoleService.getProfileRolesById(profile.getId())))
                .toList();
        return new PageImpl<>(response, PageRequest.of(page, size), profiles.getTotalElements());
    }

    public String deleteByProfileId(Integer profileId) {
        if(profileRepository.findByIdAndVisibleTrue(profileId).isEmpty()){
            throw new ItemNotFoundException("Profile is not found");
        }
        int result = profileRepository.updateVisible(profileId);

        return (result > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public ProfileResponseDTO updateProfile(Integer profileId, ProfileRequestDTO dto) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        Optional<Profile> usernameOptional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if(usernameOptional.isPresent() && !profileId.equals(usernameOptional.get().getId())){
            throw new AppBadRequestException("Username belong to other user");
        }
        Profile profile = optional.get();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setStatus(dto.getStatus());

        profileRepository.save(profile);

        profileRoleService.merge(profileId, dto.getRoleList());

        return toResponseDTO(profile, dto.getRoleList());
    }

    public String updateDetail(Integer profileId, ProfileDetailUpdateDTO dto) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile not foud");
        }
        Profile profile = optional.get();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profileRepository.save(profile);
        return "Successfully updated";
    }

    public String updatePassword(Integer profileId, ProfileUpdatePasswordDTO pDto) {
        Optional<Profile> optional = profileRepository.findByIdAndVisibleTrue(profileId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Profile not found");
        }
        Profile profile = optional.get();
        if(!profile.getPassword().equals(MD5Encode.encode(pDto.getCurrentPassword()))){
            throw new AppBadRequestException("Current password is wrong");
        }
        profile.setPassword(MD5Encode.encode(pDto.getNewPassword()));
        profileRepository.save(profile);

        return "Successfully updated";
    }
}
