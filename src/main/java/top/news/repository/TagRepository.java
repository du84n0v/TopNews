package top.news.repository;

import org.springframework.data.repository.CrudRepository;
import top.news.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    Optional<TagEntity> findByName(String name);

    List<TagEntity> findAll();
}
