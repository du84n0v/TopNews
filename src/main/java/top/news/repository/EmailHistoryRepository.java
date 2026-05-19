package top.news.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.EmailHistory;

import java.time.LocalDateTime;

public interface EmailHistoryRepository extends CrudRepository<EmailHistory, Integer> {

    Page<EmailHistory> findAllByToEmailOrderByCreatedDateDesc(String toEmail, Pageable pageable);

    Page<EmailHistory> findByCreatedDateBetweenOrderByCreatedDateDesc(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    @Query("SELECT COUNT(eh) FROM EmailHistory eh " +
            " WHERE eh.toEmail = ?1 AND eh.createdDate >= ?2 ")
    int countByToEmailAfter(String toAccount, LocalDateTime from);
}
