package top.news.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.SmsHistory;

import java.time.LocalDateTime;

public interface SmsHistoryRepository extends CrudRepository<SmsHistory, Integer> {

    @Query("SELECT COUNT(sh) FROM SmsHistory sh " +
            " WHERE sh.phone = ?1 AND sh.createdDate >= ?2")
    int countByPhoneAfter(String phone, LocalDateTime from);
}
