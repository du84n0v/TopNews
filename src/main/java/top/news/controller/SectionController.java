package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.section.SectionRequestDTO;
import top.news.dto.section.SectionResponseDTO;
import top.news.entity.SectionEntity;
import top.news.enums.AppLanguage;
import top.news.service.SectionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1//section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SectionRequestDTO dto){
        return ResponseEntity.ok(sectionService.createSection(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/by-id/{sectionId}")
    public ResponseEntity<String> updateById(@PathVariable Integer sectionId,
                                             @RequestBody SectionRequestDTO dto){
        return ResponseEntity.ok(sectionService.updateSectionById(sectionId, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/detele/by-id/{sectionId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer sectionId){
        return ResponseEntity.ok(sectionService.deleteSectionById(sectionId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<SectionEntity>> list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer size){
        return ResponseEntity.ok(sectionService.getSectionList(page-1, size));
    }

    @GetMapping("/by-lang")
    public ResponseEntity<List<SectionResponseDTO>> byLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang){
        return ResponseEntity.ok(sectionService.getSectionsByLang(lang));
    }
}
