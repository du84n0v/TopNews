package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.category.CategoryRequestDTO;
import top.news.dto.category.CategoryResponseDTO;
import top.news.entity.Category;
import top.news.enums.AppLanguage;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.mapper.CategoryMapper;
import top.news.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String createCategory(CategoryRequestDTO dto) {
        Optional<Category> optional = categoryRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(optional.isPresent()){
            throw new AppBadRequestException("Category key already exists");
        }
        Category category = save(dto);
        category.setVisible(Boolean.TRUE);
        category.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(category);

        return "Successfully created";
    }

    private Category save(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setOrderNumber(dto.getOrderNumber());
        category.setNameUz(dto.getNameUz());
        category.setNameRu(dto.getNameRu());
        category.setNameEn(dto.getNameEn());
        category.setKey(dto.getKey());
        return category;
    }

    public String updateCategoryById(Integer categoryId, CategoryRequestDTO dto) {
        Optional<Category> optional = categoryRepository.findByIdAndVisibleTrue(categoryId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }

        Optional<Category> keyOptional = categoryRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(keyOptional.isPresent() && !categoryId.equals(keyOptional.get().getId())){
            throw new AppBadRequestException("Category presents");
        }
        Category category = save(dto);
        category.setId(optional.get().getId());
        category.setVisible(Boolean.TRUE);
        category.setCreatedDate(optional.get().getCreatedDate());

        categoryRepository.save(category);

        return "Successfully updated";
    }

    public String deleteCategoryById(Integer categoryId) {
        Optional<Category> optional = categoryRepository.findByIdAndVisibleTrue(categoryId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Category is not found");
        }

        return (categoryRepository.delete(categoryId) > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Category> getCategoryList() {
        Iterable<Category> categories = categoryRepository.findAllByVisibleTrue();

        List<Category> response = new LinkedList<>();
        for (Category category : categories) {
            response.add(category);
        }
        return response;
    }

    public List<CategoryResponseDTO> getCategoriesByLang(AppLanguage lang) {
        List<CategoryMapper> mappers = categoryRepository.findByLang(lang.name());
        List<CategoryResponseDTO> response = new LinkedList<>();
        for (CategoryMapper mapper : mappers) {
            CategoryResponseDTO dto = new CategoryResponseDTO();
            dto.setId(mapper.getId());
            dto.setKey(mapper.getKey());
            dto.setName(mapper.getName());
            response.add(dto);
        }
        return response;
    }
}
