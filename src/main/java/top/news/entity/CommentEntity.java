package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
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
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "visible")
    private Boolean visible = true;
}
