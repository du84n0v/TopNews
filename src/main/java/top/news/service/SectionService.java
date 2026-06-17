package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.news.dto.section.SectionRequestDTO;
import top.news.dto.section.SectionResponseDTO;
import top.news.entity.SectionEntity;
import top.news.enums.AppLanguage;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.mapper.SectionMapper;
import top.news.repository.SectionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public String createSection(SectionRequestDTO dto) {
        Optional<SectionEntity> optional = sectionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(optional.isPresent()){
            throw new AppBadRequestException("SectionEntity key already exists");
        }
        SectionEntity section = save(dto);
        section.setVisible(Boolean.TRUE);
        section.setCreatedDate(LocalDateTime.now());

        sectionRepository.save(section);

        return "Successfully created";
    }

    private SectionEntity save(SectionRequestDTO dto) {
        SectionEntity section = new SectionEntity();
        section.setOrderNumber(dto.getOrderNumber());
        section.setNameUz(dto.getNameUz());
        section.setNameRu(dto.getNameRu());
        section.setNameEn(dto.getNameEn());
        section.setKey(dto.getKey());
        return section;
    }

    public String updateSectionById(Integer sectionId, SectionRequestDTO dto) {
        Optional<SectionEntity> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("CategoryEntity not found");
        }
        Optional<SectionEntity> keyOptional = sectionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(keyOptional.isPresent() && !sectionId.equals(keyOptional.get().getId())){
            throw new AppBadRequestException("SectionEntity key belong to other section");
        }
        SectionEntity section = save(dto);
        section.setId(sectionId);
        section.setVisible(Boolean.TRUE);
        section.setCreatedDate(optional.get().getCreatedDate());
        sectionRepository.save(section);

        return "Successfully updated";
    }

    public String deleteSectionById(Integer sectionId) {
        Optional<SectionEntity> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("CategoryEntity is not found");
        }
        return (sectionRepository.delete(sectionId) > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public Page<SectionEntity> getSectionList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SectionEntity> sections = sectionRepository.findAllByVisibleTrue(pageable);

        List<SectionEntity> response = new LinkedList<>();
        for (SectionEntity section : sections) {
            response.add(section);
        }
        return new PageImpl<>(response, PageRequest.of(page, size), sections.getTotalElements());
    }

    public List<SectionResponseDTO> getSectionsByLang(AppLanguage lang) {
        List<SectionMapper> mappers = sectionRepository.getByLang(lang.name());
        List<SectionResponseDTO> response = new LinkedList<>();
        for (SectionMapper mapper : mappers) {
            SectionResponseDTO dto = new SectionResponseDTO();
            dto.setId(mapper.getId());
            dto.setKey(mapper.getKey());
            dto.setName(mapper.getName());
            response.add(dto);
        }
        return response;
    }

}
