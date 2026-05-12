package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.section.SectionByLangDTO;
import top.news.dto.section.SectionDTO;
import top.news.entity.Section;
import top.news.exception.ItemNotFoundException;
import top.news.repository.SectionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public String createSection(SectionDTO dto) {
        Section section = save(dto);
        section.setVisible(true);
        section.setCreatedDate(LocalDateTime.now());

        sectionRepository.save(section);

        return "Successfully created";
    }

    private Section save(SectionDTO dto) {
        Section section = new Section();
        section.setOrderNumber(dto.getOrderNumber());
        section.setNameUz(dto.getNameUz());
        section.setNameRu(dto.getNameRu());
        section.setNameEn(dto.getNameEn());
        section.setKey(dto.getKey());
        return section;
    }

    public String updateSectionById(Integer sectionId, SectionDTO dto) {
        Optional<Section> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }
        Section section = save(dto);

        sectionRepository.save(section);

        return "Successfully updated";
    }

    public String deleteSectionById(Integer sectionId) {
        Optional<Section> optional = sectionRepository.findByIdAndVisibleTrue(sectionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }

        int result = sectionRepository.delete(sectionId);

        return (result > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Section> getSectionList() {
        Iterable<Section> sections = sectionRepository.findAllByVisibleTrue();

        List<Section> response = new LinkedList<>();
        for (Section section : sections) {
            response.add(section);
        }
        return response;
    }

    public List<SectionByLangDTO> getSectionsByLang(String lang) {
        Iterable<Section> sections = sectionRepository.findAllByVisibleTrue();

        List<SectionByLangDTO> response = new LinkedList<>();
        for (Section section : sections) {
            SectionByLangDTO current = new SectionByLangDTO();
            current.setId(section.getId());
            current.setKey(section.getKey());

            switch(lang){
                case "uz" -> current.setName(section.getNameUz());
                case "ru" -> current.setName(section.getNameRu());
                case "en" -> current.setName(section.getNameEn());
            }

            response.add(current);
        }

        return response;
    }

}
