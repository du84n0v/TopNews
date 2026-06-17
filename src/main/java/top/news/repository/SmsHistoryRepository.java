package top.news.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.SmsHistoryEntity;

import java.time.LocalDateTime;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer> {

    @Query("SELECT COUNT(sh) FROM SmsHistoryEntity sh " +
            " WHERE sh.phone = ?1 AND sh.createdDate >= ?2")
    int countByPhoneAfter(String phone, LocalDateTime from);
}
