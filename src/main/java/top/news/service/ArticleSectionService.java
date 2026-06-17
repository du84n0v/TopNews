package top.news.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.ArticleSectionEntity;
import top.news.repository.ArticleSectionRepository;

import java.util.List;

@Service
public class ArticleSectionService {

    @Autowired
    private ArticleSectionRepository articleSectionRepository;

    @Transactional
    public void merge(String articleId, List<Integer> sectionIdList) {
        deleteAllByArticleId(articleId);

        for (Integer id : sectionIdList) {
            ArticleSectionEntity articleSection = new ArticleSectionEntity();
            articleSection.setArticleId(articleId);
            articleSection.setSectionId(id);
            articleSectionRepository.save(articleSection);
        }
    }

    private void deleteAllByArticleId(String articleId) {
        articleSectionRepository.deleteAllByArticleId(articleId);
    }
}
