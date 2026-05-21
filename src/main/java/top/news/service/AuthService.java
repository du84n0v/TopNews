package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.auth.RegistrationDTO;
import top.news.dto.auth.VerificationDTO;
import top.news.entity.Profile;
import top.news.entity.VerificationAttempt;
import top.news.enums.ProfileRoleEnum;
import top.news.enums.ProfileStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.repository.EmailHistoryRepository;
import top.news.repository.ProfileRepository;
import top.news.repository.VerificationAttemptRepository;
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
    @Autowired
    private VerificationAttemptRepository attemptRepository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

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
        profile.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        profile.setVisible(Boolean.TRUE);
        profile.setCreatedDate(LocalDateTime.now());

        profileRepository.save(profile);

        profileRoleService.merge(profile.getId(), List.of(ProfileRoleEnum.ROLE_USER));

        mailSenderService.verificationCode(profile.getUsername());

        return "Registration success. Please verify your email or phone.";
    }

    public String verify(VerificationDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        VerificationAttempt attempt = attemptRepository.findByUsername(dto.getUsername());
        if(attempt == null){
            VerificationAttempt verificationAttempt = new VerificationAttempt();
            verificationAttempt.setUsername(dto.getUsername());
            verificationAttempt.setAttemptCount(0);
            attempt = attemptRepository.save(verificationAttempt);
        }

        if(attempt.getAttemptCount() >= 5 && attempt.getLastAttempt() != null){
            LocalDateTime expiryTime = attempt.getLastAttempt().plusMinutes(2);
            if(expiryTime.isAfter(now)){
                throw new AppBadRequestException("Too many attempt. Please wait 2 minutes");
            }
            else{
                attempt.setAttemptCount(0);
            }
        }

        String actualCode = emailHistoryRepository.findCodeByToEmail(dto.getUsername());
        if(actualCode != null && !actualCode.equals(dto.getCode())){
            attempt.setAttemptCount(attempt.getAttemptCount()+1);
            attempt.setLastAttempt(now);
            attemptRepository.save(attempt);

            int remaining = 5 - attempt.getAttemptCount();
            throw new AppBadRequestException("Wrong code: " + (remaining > 0 ? remaining + "-attempt left" : "Please try 2 minutes later"));
        }

        Optional<Profile> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if(optional.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        Profile profile = optional.get();
        profile.setStatus(ProfileStatusEnum.ACTIVE);
        profileRepository.save(profile);

        attemptRepository.delete(attempt);

        return "Successfully activated. You can login by now";
    }

//    public ProfileResponseDTO login(LoginDTO login) {
//        Optional<Profile> optional = profileRepository.findByUsernameAndPasswordAndStatusAndVisibleTrue(login.getUsername(), login.getPassword(), ProfileStatusEnum.ACTIVE);
//        if(optional.isEmpty()){
//            throw new ItemNotFoundException("Username or Password is wrong");
//        }
//
//    }
}
