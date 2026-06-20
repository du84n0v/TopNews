package top.news.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.news.dto.article.ArticleFilterDTO;
import top.news.dto.article.ArticleRequestDTO;
import top.news.dto.article.ArticleShortInfoDTO;
import top.news.dto.article.ArticleStatusDTO;
import top.news.entity.ArticleEntity;
import top.news.enums.ArticleStatusEnum;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.mapper.ArticleShortInfoMapper;
import top.news.repository.ArticleRepository;
import top.news.repository.custom.CustomArticleRepository;
import top.news.util.SpringSecurityUtil;

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

    @Autowired
    private CustomArticleRepository customRepository;

    @Autowired
    private AttachService attachService;

    public ArticleShortInfoDTO createArticle(ArticleRequestDTO dto) {
        ArticleEntity article = dtoToEntity(dto);
        article.setModeratorId(SpringSecurityUtil.getCurrentProfileId());

        articleRepository.save(article);

        articleCategoryService.merge(article.getId(), dto.getCategoryIdList());

        articleSectionService.merge(article.getId(), dto.getSectionIdList());

        return entityToShortDto(article);
    }

    @Transactional
    public ArticleShortInfoDTO updateArticle(String articleId, ArticleRequestDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.findByIdAndVisibleTrue(articleId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("ArticleEntity not foud");
        }
        ArticleEntity article = optional.get();
        if(!article.getModeratorId().equals(SpringSecurityUtil.getCurrentProfileId())){
            throw new AppBadRequestException("This is not your article");
        }
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        if(dto.getImageId() != null){
            if(article.getImageId() != null) attachService.deleteContent(article.getImageId());
            article.setImageId(dto.getImageId());
        }
        if(dto.getRegionId() != null) article.setRegionId(dto.getRegionId());
        article.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        article.setPublishedDate(null);
        article.setPublisherId(null);

        articleCategoryService.merge(articleId, dto.getCategoryIdList());
        articleSectionService.merge(articleId, dto.getSectionIdList());

        return entityToShortDto(article);
    }

    public String deleteArticleById(String articleId) {
        int effRow = articleRepository.deleteArticleById(articleId);
        if(effRow > 0){
            return "Successfully deleted";
        }
        throw new ItemNotFoundException("ArticleEntity not found");
    }

    public String changeArticleStatus(String articleId, ArticleStatusDTO dto) {
        int effRow = articleRepository.changeStatus(articleId, dto.getStatus(), SpringSecurityUtil.getCurrentProfileId(), LocalDateTime.now());

        if(effRow > 0){
            return "Successfully changed";
        }
        throw new ItemNotFoundException("ArticleEntity not found");
    }

    public Page<ArticleShortInfoDTO> getLastNArticleBySectionId(Integer sectionId, int page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pages = articleRepository.findNArticleBySectionId(sectionId, pageRequest);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::mapperToShortDto)
                .toList();
        return new PageImpl<>(response, pageRequest, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLast12(List<String> ids, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pages = articleRepository.getLast12(ids, pageable);

        List<ArticleShortInfoDTO> response = pages.stream()
                .map(this::mapperToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLastNArticleByCategoryId(Integer categoryId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pages = articleRepository.findNArticleByCategoryId(categoryId, pageable);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::mapperToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getLastNArticleByRegionId(Integer regionId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pages = articleRepository.findNArticleByRegionId(regionId, pageable);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::mapperToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> getMostReadExcept(String articleId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> pages = articleRepository.find4MostReadExcept(articleId, pageable);

        List<ArticleShortInfoDTO> response = pages.getContent()
                .stream()
                .map(this::mapperToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Integer increaseViewCountByArticleId(String articleId) {
        int effRow =  articleRepository.increaseViewCountById(articleId);
        if(effRow != 0){
            return articleRepository.findViewCountById(articleId);
        }
        else{
            throw new ItemNotFoundException("ArticleEntity not found");
        }
    }

    public Integer increaseShareCountByArticleId(String articleId) {
        int effRow =  articleRepository.increaseShareCountById(articleId);
        if(effRow != 0){
            return articleRepository.findShareCountById(articleId);
        }
        else {
            throw new ItemNotFoundException("ArticleEntity not found");
        }
    }

    public Page<ArticleShortInfoDTO> filterForEveryOne(ArticleFilterDTO filterDto, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        filterDto.setStatus(ArticleStatusEnum.PUBLISHED);
        Page<Object[]> pages = customRepository.filter(filterDto, page, size);

        List<ArticleShortInfoDTO> response = pages.stream()
                .map(this::objectToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    public Page<ArticleShortInfoDTO> filterForModerator(ArticleFilterDTO dto, Integer page, Integer size) {
        dto.setModeratorId(SpringSecurityUtil.getCurrentProfileId());
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> pages =customRepository.filter(dto, page, size);

        List<ArticleShortInfoDTO> response = pages.stream()
                .map(this::objectToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());
    }

    private ArticleShortInfoDTO entityToShortDto(ArticleEntity article) {
        ArticleShortInfoDTO response = new ArticleShortInfoDTO();
        response.setArticleId(article.getId());
        response.setTitle(article.getTitle());
        response.setDescription(article.getDescription());
        response.setPublishedDate(article.getCreatedDate());
        if(article.getImageId() != null) response.setContent(attachService.openDTO(article.getImageId()));

        return response;
    }

    private ArticleEntity dtoToEntity(ArticleRequestDTO dto) {
        ArticleEntity article = new ArticleEntity();
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

    private ArticleShortInfoDTO objectToShortDto(Object[] obj){
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setArticleId((String) obj[0]);
        dto.setTitle((String) obj[1]);
        dto.setDescription((String) obj[2]);
        if(obj[3] != null) dto.setContent(attachService.openDTO((String) obj[3]));
        dto.setPublishedDate((LocalDateTime) obj[4]);

        return dto;
    }

    private ArticleShortInfoDTO mapperToShortDto(ArticleShortInfoMapper mapper){
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setArticleId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        if(mapper.getImageId() != null) dto.setContent(attachService.openDTO(mapper.getImageId()));
        dto.setPublishedDate(mapper.getPublishedDate());

        return dto;
    }

    public Page<ArticleShortInfoDTO> filterForPublisher(ArticleFilterDTO dto, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> pages = customRepository.filter(dto, page, size);

        List<ArticleShortInfoDTO> response = pages.stream()
                .map(this::objectToShortDto)
                .toList();

        return new PageImpl<>(response, pageable, pages.getTotalElements());

    }

    public void articleExists(String articleId) {
        articleRepository.findByIdAndVisibleTrue(articleId)
                .orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }
}
