package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.news.dto.section.SectionShortDTO;
import top.news.entity.ArticleSectionEntity;
import top.news.repository.ArticleSectionRepository;

import java.util.List;

@Service
public class ArticleSectionService {

    @Autowired
    private ArticleSectionRepository articleSectionRepository;

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(readOnly = true)
    public List<SectionShortDTO> getArticleSection(String articleId) {
        List<ArticleSectionEntity> ans = articleSectionRepository.findAllByArticleId(articleId);

        return ans.stream()
                .map(as -> new SectionShortDTO(as.getSection().getKey(), as.getSection().getNameUz()))
                .toList();
    }
}
