package top.news.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.ArticleCategory;
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
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(id);
            articleCategoryRepository.save(articleCategory);
        }
    }

    private void deleteAllByArticleId(String articleId) {
        articleCategoryRepository.deleteAllByArticleId(articleId);
    }
}
