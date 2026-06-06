package top.news.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.news.entity.Profile;
import top.news.entity.ProfileRole;
import top.news.enums.ProfileRoleEnum;
import top.news.exception.ItemNotFoundException;
import top.news.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUsername: " + username);
        Optional<Profile> optional = profileRepository.findByUsernameAndVisibleTrue(username);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("User not found");
        }
        Profile profile = optional.get();
        List<ProfileRoleEnum> roleEnums = new ArrayList<>();
        for (ProfileRole role : profile.getRoles()) {
            roleEnums.add(role.getRole());
        }
        return new CustomUserDetails(profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getStatus(),
                roleEnums);
    }
}
