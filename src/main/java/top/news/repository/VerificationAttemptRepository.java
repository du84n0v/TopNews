package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.VerificationAttemptEntity;

public interface VerificationAttemptRepository extends CrudRepository<VerificationAttemptEntity, Integer> {

    VerificationAttemptEntity findByUsername(String username);
}
