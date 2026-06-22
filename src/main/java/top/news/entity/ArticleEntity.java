package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import top.news.enums.ArticleStatusEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "text")
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatusEnum status;

    @Column(name = "read_time")
    private Integer readTime;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "dislike_count")
    private Integer dislikeCount;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
