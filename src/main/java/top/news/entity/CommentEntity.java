package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SQLRestriction("visible = true")
@Table(name = "comment", indexes = {
        @Index(name = "idx_comment_article_id", columnList = "article_id"),
        @Index(name = "idx_comment_profile_id", columnList = "profile_id")
})
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id", nullable = false)
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private Integer replyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private CommentEntity replyComment;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "visible")
    private Boolean visible = true;
}
