package top.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "to_email", nullable = false)
    private String toEmail;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
