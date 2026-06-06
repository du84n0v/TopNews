package top.news.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.news.enums.ProfileRoleEnum;
import top.news.enums.ProfileStatusEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails  implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private ProfileStatusEnum status;
    private List<ProfileRoleEnum> roles;

    public CustomUserDetails(Integer id,
                             String username,
                             String password,
                             ProfileStatusEnum status,
                             List<ProfileRoleEnum> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (ProfileRoleEnum role : roles) {
            list.add(new SimpleGrantedAuthority(role.name()));
        }
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == ProfileStatusEnum.ACTIVE;
    }
}
