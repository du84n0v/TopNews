package top.news.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.news.enums.ProfileStatusEnum;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails  implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private ProfileStatusEnum status;
    private List<String> roles;

    public CustomUserDetails(Integer id, String username, String password,
                             String name, String surname,
                             ProfileStatusEnum status,
                             List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == ProfileStatusEnum.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
