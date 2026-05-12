package top.news.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.category.CategoryByLangDTO;
import top.news.dto.category.CategoryDTO;
import top.news.entity.Category;
import top.news.entity.Region;
import top.news.exception.ItemNotFoundException;
import top.news.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String createCategory(CategoryDTO dto) {
        Category category = save(dto);
        category.setVisible(true);
        category.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(category);

        return "Successfully created";
    }

    private Category save(CategoryDTO dto) {
        Category category = new Category();
        category.setOrderNumber(dto.getOrderNumber());
        category.setNameUz(dto.getNameUz());
        category.setNameRu(dto.getNameRu());
        category.setNameEn(dto.getNameEn());
        category.setKey(dto.getKey());
        return category;
    }

    public String updateCategoryById(Integer categoryId, CategoryDTO dto) {
        Optional<Category> optional = categoryRepository.findByIdAndVisibleTrue(categoryId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }
        Category category = save(dto);

        categoryRepository.save(category);

        return "Successfully updated";
    }

    public String deleteCategoryById(Integer categoryId) {
        Optional<Category> optional = categoryRepository.findByIdAndVisibleTrue(categoryId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }

        int result = categoryRepository.delete(categoryId);

        return (result > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Category> getCategoryList() {
        Iterable<Category> categories = categoryRepository.findAllByVisibleTrue();

        List<Category> response = new LinkedList<>();
        for (Category category : categories) {
            response.add(category);
        }
        return response;
    }

    public List<CategoryByLangDTO> getCategoriesByLang(String lang) {
        Iterable<Category> categories = categoryRepository.findAllByVisibleTrue();

        List<CategoryByLangDTO> response = new LinkedList<>();
        for (Category category : categories) {
            CategoryByLangDTO current = new CategoryByLangDTO();
            current.setId(category.getId());
            current.setKey(category.getKey());

            switch(lang){
                case "uz" -> current.setName(category.getNameUz());
                case "ru" -> current.setName(category.getNameRu());
                case "en" -> current.setName(category.getNameEn());
            }

            response.add(current);
        }

        return response;
    }
}
