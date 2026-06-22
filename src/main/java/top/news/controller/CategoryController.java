package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.news.dto.category.CategoryRequestDTO;
import top.news.dto.category.CategoryResponseDTO;
import top.news.entity.CategoryEntity;
import top.news.enums.AppLanguage;
import top.news.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1//category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/by-id/{categoryId}")
    public ResponseEntity<String> updateById(@PathVariable Integer categoryId,
                                             @RequestBody CategoryRequestDTO dto){
        return ResponseEntity.ok(categoryService.updateCategoryById(categoryId, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/detele/by-id/{categoryId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(categoryService.deleteCategoryById(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<CategoryEntity>> list(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @GetMapping("/by-lang")
    public ResponseEntity<List<CategoryResponseDTO>> byLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang){
        return ResponseEntity.ok(categoryService.getCategoriesByLang(lang));
    }
}
