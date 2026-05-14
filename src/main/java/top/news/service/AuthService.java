package top.news.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.AuthDTO;
import top.news.entity.Profile;
import top.news.enums.ProfileStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.repository.ProfileRepository;
import top.news.util.MD5Encode;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    public String register(@Valid AuthDTO dto) {
        Optional<Profile> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if(optional.isPresent()){
            throw new AppBadRequestException("Email/Phone already exists");
        }
        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(MD5Encode.encode(dto.getPassword()));
        profile.setStatus(ProfileStatusEnum.ACTIVE);
        profile.setVisible(true);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        return "Registration success. Please verify your email/phone.";
    }
}
