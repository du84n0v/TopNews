package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.auth.RegistrationDTO;
import top.news.entity.Profile;
import top.news.enums.ProfileRoleEnum;
import top.news.enums.ProfileStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.repository.ProfileRepository;
import top.news.util.MD5Encode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private MailSenderService mailSenderService;

    public String register(RegistrationDTO dto) {
        Optional<Profile> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if(optional.isPresent()) {
            Profile profile = optional.get();
            if (profile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
                profileRoleService.deleteProfileRoles(profile.getId());
                profileRepository.delete(profile);
            } else {
                throw new AppBadRequestException("Email or Phone already exists");
            }
        }
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(MD5Encode.encode(dto.getPassword()));
        profile.setStatus(ProfileStatusEnum.ACTIVE);
        profile.setVisible(Boolean.TRUE);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        profileRoleService.merge(profile.getId(), List.of(ProfileRoleEnum.ROLE_USER));

        mailSenderService.verificationCode(profile.getUsername(), profile.getId());

        return "Registration success. Please verify your email or phone.";
    }
}
