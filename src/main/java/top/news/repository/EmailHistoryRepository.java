package top.news.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.EmailHistoryEntity;

import java.time.LocalDateTime;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    Page<EmailHistoryEntity> findAllByToEmailOrderByCreatedDateDesc(String toEmail, Pageable pageable);

    Page<EmailHistoryEntity> findByCreatedDateBetweenOrderByCreatedDateDesc(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    @Query("SELECT COUNT(eh) FROM EmailHistoryEntity eh " +
            " WHERE eh.toEmail = ?1 AND eh.createdDate >= ?2 ")
    int countByToEmailAfter(String toAccount, LocalDateTime from);

    EmailHistoryEntity findTopByToEmailOrderByCreatedDateDesc(String username);
}
