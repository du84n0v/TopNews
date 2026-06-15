package top.news.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private List<String> roles;
}