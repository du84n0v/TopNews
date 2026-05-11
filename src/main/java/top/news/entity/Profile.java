package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.ProfileStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true, nullable = false)
    private String username;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column
    private Boolean visible;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "photo_id")
    private Integer photoId;
}
