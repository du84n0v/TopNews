package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.news.dto.article.ArticleRequestDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.dto.article.ArticleStatusDTO;
import top.news.entity.Article;
import top.news.enums.ArticleStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleSectionService articleSectionService;

    public ArticleShortInfoDTO createArticle(ArticleRequestDTO dto) {
        Article article = dtoToEntity(dto);

        articleRepository.save(article);

        articleCategoryService.merge(article.getId(), dto.getCategoryIdList());

        articleSectionService.merge(article.getId(), dto.getSectionIdList());

        return toShortDto(article);
    }

    private ArticleShortInfoDTO toShortDto(Article article) {
        ArticleShortInfoDTO response = new ArticleShortInfoDTO();
        response.setArticleId(article.getId());
        response.setTitle(article.getTitle());
        response.setDescription(article.getDescription());
        response.setCreatedDate(article.getCreatedDate());
        if(article.getImageId() != null) response.setImageId(article.getImageId());

        return response;
    }

    private Article dtoToEntity(ArticleRequestDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        if(dto.getImageId() != null) article.setImageId(dto.getImageId());
        if(dto.getRegionId() != null) article.setRegionId(dto.getRegionId());
        article.setVisible(Boolean.TRUE);
        article.setCreatedDate(LocalDateTime.now());
        article.setReadTime(0);
        article.setSharedCount(0);
        article.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        article.setViewCount(0);
        return article;
    }

    public ArticleShortInfoDTO updateArticle(String articleId, Integer moderatorId, ArticleRequestDTO dto) {
        Optional<Article> optional = articleRepository.findByIdAndVisibleTrue(articleId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Article not foud");
        }
        Article article = optional.get();
        if(!article.getModeratorId().equals(moderatorId)){
            throw new AppBadRequestException("This is not your article");
        }
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        if(dto.getImageId() != null) article.setImageId(dto.getImageId());
        if(dto.getRegionId() != null) article.setRegionId(dto.getRegionId());
        article.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        article.setPublishedDate(null);
        article.setPublisherId(null);

        articleCategoryService.merge(articleId, dto.getCategoryIdList());
        articleSectionService.merge(articleId, dto.getSectionIdList());

        return toShortDto(article);
    }

    public String deleteArticleById(String articleId) {
        Optional<Article> optional = articleRepository.findByIdAndVisibleTrue(articleId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Article not found");
        }
        Article article = optional.get();
        article.setVisible(Boolean.FALSE);
        articleRepository.save(article);

        return "Successfully deleted";
    }

    public String changeArticleStatus(String articleId, Integer publisherId, ArticleStatusDTO dto) {
        Optional<Article> optional = articleRepository.findByIdAndVisibleTrue(articleId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Article not found");
        }
        Article article = optional.get();
        article.setStatus(dto.getStatus());
        article.setPublisherId(publisherId);

        articleRepository.save(article);

        return "Successfully changed";
    }

    public Page<ArticleShortInfoDTO> getLastNArticleBySectionId(Integer sectionId, int page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Article> pages = articleRepository.findNArticleBySectionId(sectionId, pageRequest);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::toShortDto)
                .toList();
        return new PageImpl<>(response, pageRequest, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLast12(List<String> ids, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> pages = articleRepository.getLast12(ids, pageable);

        List<ArticleShortInfoDTO> response = pages.stream()
                .map(this::toShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLastNArticleByCategoryId(Integer categoryId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> pages = articleRepository.findNArticleByCategoryId(categoryId, pageable);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::toShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLastNArticleByRegionId(Integer regionId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> pages = articleRepository.findNArticleByRegionId(regionId, pageable);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::toShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }
}
