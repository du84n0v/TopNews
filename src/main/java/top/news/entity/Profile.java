package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProfileStatusEnum status;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "photo_id")
    private String photoId;

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER
    )
    List<ProfileRole> roles;
}
