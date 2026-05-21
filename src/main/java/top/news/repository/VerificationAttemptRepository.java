package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.VerificationAttempt;

public interface VerificationAttemptRepository extends CrudRepository<VerificationAttempt, Integer> {

    VerificationAttempt findByUsername(String username);
}
