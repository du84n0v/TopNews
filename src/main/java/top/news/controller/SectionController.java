package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.section.SectionRequestDTO;
import top.news.dto.section.SectionResponseDTO;
import top.news.entity.Section;
import top.news.enums.AppLanguage;
import top.news.service.SectionService;

import java.util.List;

@RestController
@RequestMapping("section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SectionRequestDTO dto){
        return ResponseEntity.ok(sectionService.createSection(dto));
    }

    @PutMapping("/update-by-id/{sectionId}")
    public ResponseEntity<String> updateById(@PathVariable Integer sectionId,
                                             @RequestBody SectionRequestDTO dto){
        return ResponseEntity.ok(sectionService.updateSectionById(sectionId, dto));
    }

    @PutMapping("/detele-by-id/{sectionId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer sectionId){
        return ResponseEntity.ok(sectionService.deleteSectionById(sectionId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Section>> list(){
        return ResponseEntity.ok(sectionService.getSectionList());
    }

    @GetMapping("/by-lang")
    public ResponseEntity<List<SectionResponseDTO>> byLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang){
        return ResponseEntity.ok(sectionService.getSectionsByLang(lang));
    }
}
