package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.tag.TagDTO;
import top.news.dto.tag.TagInfoDTO;
import top.news.entity.TagEntity;
import top.news.enums.TagStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Boolean create(TagDTO dto) {
        Optional<TagEntity> optional = tagRepository.findByName(dto.getName().toLowerCase());
        if(optional.isPresent()){
            throw new AppBadRequestException("This tag is already exist");
        }

        TagEntity tag = new TagEntity();
        tag.setName(dto.getName().toLowerCase());
        tag.setStatus(TagStatusEnum.ACTIVE);
        tag.setCreatedDate(LocalDateTime.now());

        tagRepository.save(tag);

        return Boolean.TRUE;
    }

    public List<TagInfoDTO> getList() {
        return tagRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    private TagInfoDTO entityToDto(TagEntity tag){
        return new TagInfoDTO(tag.getId(), tag.getName(), tag.getStatus());
    }
}
