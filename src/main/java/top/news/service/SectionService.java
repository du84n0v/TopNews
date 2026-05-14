package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.section.SectionRequestDTO;
import top.news.dto.section.SectionResponseDTO;
import top.news.entity.Section;
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
        Optional<Section> optional = sectionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(optional.isPresent()){
            throw new AppBadRequestException("Section key already exists");
        }
        Section section = save(dto);
        section.setVisible(Boolean.TRUE);
        section.setCreatedDate(LocalDateTime.now());

        sectionRepository.save(section);

        return "Successfully created";
    }

    private Section save(SectionRequestDTO dto) {
        Section section = new Section();
        section.setOrderNumber(dto.getOrderNumber());
        section.setNameUz(dto.getNameUz());
        section.setNameRu(dto.getNameRu());
        section.setNameEn(dto.getNameEn());
        section.setKey(dto.getKey());
        return section;
    }

    public String updateSectionById(Integer sectionId, SectionRequestDTO dto) {
        Optional<Section> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        Optional<Section> keyOptional = sectionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(keyOptional.isPresent() && !sectionId.equals(keyOptional.get().getId())){
            throw new AppBadRequestException("Section key belong to other section");
        }
        Section section = save(dto);
        section.setId(sectionId);
        section.setVisible(Boolean.TRUE);
        section.setCreatedDate(optional.get().getCreatedDate());
        sectionRepository.save(section);

        return "Successfully updated";
    }

    public String deleteSectionById(Integer sectionId) {
        Optional<Section> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }
        return (sectionRepository.delete(sectionId) > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Section> getSectionList() {
        Iterable<Section> sections = sectionRepository.findAllByVisibleTrue();

        List<Section> response = new LinkedList<>();
        for (Section section : sections) {
            response.add(section);
        }
        return response;
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
