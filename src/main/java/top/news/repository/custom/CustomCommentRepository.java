package top.news.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import top.news.dto.comment.CommentFilterDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomCommentRepository {

    @Autowired
    private QueryPagination queryPagination;

    public Page<Object[]> filter(CommentFilterDTO dto, Integer page, Integer size) {

        StringBuilder select = new StringBuilder(
                "SELECT c.id, c.createdDate, c.updatedDate, c.content, c.replyId, c.visible, " +
                " p.id, p.name, p.surname, a.id, a.title, " +
                " (SELECT COUNT(cl) FROM CommentLikeEntity cl WHERE cl.commentId = c.id AND cl.status = top.news.enums.LikeStatusEnum.LIKE), " +
                " (SELECT COUNT(cl) FROM CommentLikeEntity cl WHERE cl.commentId = c.id AND cl.status = top.news.enums.LikeStatusEnum.DISLIKE) ");

        StringBuilder filter = new StringBuilder(" FROM CommentEntity c ");
        filter.append(" INNER JOIN c.profile p ");
        filter.append(" INNER JOIN c.article a ");
        filter.append(" WHERE c.visible = true ");

        Map<String, Object> params = new HashMap<>();

        if(dto.getCommentId() != null){
            filter.append(" AND c.id = :id ");
            params.put("id", dto.getCommentId());
        }
        if(dto.getFromCreatedDate() != null){
            LocalDateTime from = dto.getFromCreatedDate().atStartOfDay();

            filter.append(" AND c.createdDate >= :from ");
            params.put("from", from);
        }
        if(dto.getToCreatedDate() != null){
            LocalDateTime to = dto.getToCreatedDate().atTime(LocalTime.MAX);

            filter.append(" AND c.createdDate <= :to ");
            params.put("to", to);
        }
        if(dto.getProfileId() != null){
            filter.append(" AND c.profileId = :profileId");
            params.put("profileId", dto.getProfileId());
        }
        if(dto.getArticleId() != null){
            filter.append(" AND c.articleId = :articleId");
            params.put("articleId", dto.getArticleId());
        }

        String selectQuery = select.toString() + filter.toString();
        String countQuery = "SELECT COUNT(c) " + filter.toString();

        return queryPagination.getPaginationResult(
                selectQuery,
                countQuery,
                params,
                page, size,
                Object[].class);
    }
}
