package top.news.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.category.CategoryShortDTO;
import top.news.entity.ArticleCategoryEntity;
import top.news.repository.ArticleCategoryRepository;

import java.util.List;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Transactional
    public void merge(String articleId, List<Integer> categoryIdList) {
        deleteAllByArticleId(articleId);

        for (Integer id : categoryIdList) {
            ArticleCategoryEntity articleCategory = new ArticleCategoryEntity();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(id);
            articleCategoryRepository.save(articleCategory);
        }
    }

    private void deleteAllByArticleId(String articleId) {
        articleCategoryRepository.deleteAllByArticleId(articleId);
    }

    public List<CategoryShortDTO> getArticleCategories(String articleId) {
        List<ArticleCategoryEntity> ans = articleCategoryRepository.getAllByArticleId(articleId);
        return ans.stream()
                .map(ac -> new CategoryShortDTO(ac.getCategory().getKey(), ac.getCategory().getNameUz()))
                .toList();
    }
}
