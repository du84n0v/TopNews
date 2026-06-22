package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.region.RegionRequestDTO;
import top.news.dto.region.RegionResponseDTO;
import top.news.entity.RegionEntity;
import top.news.enums.AppLanguage;
import top.news.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1//region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.ok(regionService.saveRegion(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/by-id/{regionId}")
    public ResponseEntity<String> update(@PathVariable Integer regionId,
                                         @Valid @RequestBody RegionRequestDTO dto){
        return ResponseEntity.ok(regionService.updateById(regionId, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("delete/by-id/{regionId}")
    public ResponseEntity<String> delete(@PathVariable Integer regionId){
        return ResponseEntity.ok(regionService.deleteRegion(regionId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<RegionEntity>> list(){
        return ResponseEntity.ok(regionService.getList());
    }

    @GetMapping("/by-lang")
    public ResponseEntity<List<RegionResponseDTO>> byLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang){
        return ResponseEntity.ok(regionService.getRegionByLang(lang));
    }
}
