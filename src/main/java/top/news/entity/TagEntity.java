package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.TagStatusEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TagStatusEnum status = TagStatusEnum.ACTIVE;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}