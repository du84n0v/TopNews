package top.news.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import top.news.dto.article.ArticleFilterDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomArticleRepository {

    @Autowired
    private QueryPagination queryPagination;

    public Page<Object[]> filter(ArticleFilterDTO dto, Integer page, Integer size) {
        StringBuilder select = new StringBuilder("SELECT a.id, a.title, a.description,a.imageId, a.publishedDate FROM ArticleEntity a ");
        StringBuilder count = new StringBuilder("SELECT COUNT(DISTINCT a) FROM ArticleEntity a ");
        Map<String, Object> params = new HashMap<>();
        StringBuilder filter = new StringBuilder(" WHERE a.visible=true ");

        if(dto.getStatus() != null){
            filter.append(" AND a.status = :status ");
            params.put("status", dto.getStatus());
        }

        if(dto.getCategoryId() != null){
            select.append(" INNER JOIN ArticleCategoryEntity ac on a.id = ac.articleId ");
            count.append(" INNER JOIN ArticleCategoryEntity ac on a.id = ac.articleId ");

            filter.append(" AND ac.categoryId = :categoryId ");
            params.put("categoryId", dto.getCategoryId());
        }
        if(dto.getSectionId() != null){
            select.append(" INNER JOIN ArticleSectionEntity ss on ss.articleId = a.id ");
            count.append(" INNER JOIN ArticleSectionEntity ss on ss.articleId = a.id ");

            filter.append(" AND ss.sectionId = :sectionId ");
            params.put("sectionId", dto.getSectionId());
        }

        if(dto.getTitle() != null){
            filter.append(" AND LOWER(a.title) LIKE :title ");
            params.put("title", "%" + dto.getTitle().toLowerCase() + "%");
        }
        if(dto.getRegionId() != null){
            filter.append(" AND a.regionId = :regionId ");
            params.put("regionId", dto.getRegionId());
        }
        if(dto.getModeratorId() != null){
            filter.append(" AND a.moderatorId = :moderatorId ");
            params.put("moderatorId", dto.getModeratorId());
        }
        if(dto.getPublisherId() != null){
            filter.append(" AND a.publisherId = :publisherId ");
            params.put("publisherId", dto.getPublisherId());
        }
        if(dto.getCreatedDateFrom() != null){
            LocalDateTime from = dto.getCreatedDateFrom().atStartOfDay();

            filter.append(" AND a.createdDate >= :from ");
            params.put("from", from);
        }
        if(dto.getCreatedDateTo() != null){
            LocalDateTime to = dto.getCreatedDateTo().atTime(LocalTime.MAX);

            filter.append(" AND a.createdDate <= :to ");
            params.put("to", to);
        }
        if(dto.getPublishedDateFrom() != null){
            LocalDateTime from = dto.getPublishedDateFrom().atStartOfDay();

            filter.append(" AND a.publishedDate >= :publishedFrom ");
            params.put("publishedFrom", from);
        }
        if(dto.getPublishedDateTo() != null){
            LocalDateTime to = dto.getPublishedDateTo().atTime(LocalTime.MAX);

            filter.append(" AND a.publishedDate <= :publishedTo ");
            params.put("publishedTo", to);
        }

        select.append(filter);
        count.append(filter);

        return queryPagination.getPaginationResult(
                select.toString(),
                count.toString(),
                params,
                page,
                size,
                Object[].class);
    }
}
