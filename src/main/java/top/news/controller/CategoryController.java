package top.news.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.dto.category.CategoryDTO;
import top.news.service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("/update-by-id/{categoryId}")
    public ResponseEntity<String> updateById(@PathVariable Integer categoryId,
                                             @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(categoryService.updateCategoryById(categoryId, dto));
    }

    @PutMapping("/detele-by-id/{categoryId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(categoryService.deleteCategoryById(categoryId));
    }

}
