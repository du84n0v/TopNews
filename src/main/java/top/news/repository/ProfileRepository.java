package top.news.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import top.news.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Optional<Profile> findByUsernameAndVisibleTrue(String username);
}
