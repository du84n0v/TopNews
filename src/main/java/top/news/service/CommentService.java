package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import top.news.dto.article.ArticleShortDTO;
import top.news.dto.comment.*;
import top.news.entity.CommentEntity;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.mapper.CommentArticleMapper;
import top.news.mapper.CommentRepliesMapper;
import top.news.repository.CommentRepository;
import top.news.repository.custom.CustomCommentRepository;
import top.news.util.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CustomCommentRepository customRepository;

    public String create(CommentDTO dto) {
        articleService.articleExists(dto.getArticleId());

        CommentEntity comment = new CommentEntity();
        comment.setContent(dto.getContent());
        comment.setArticleId(dto.getArticleId());
        comment.setProfileId(SpringSecurityUtil.getCurrentProfileId());
        if(dto.getReplyId() != null) comment.setReplyId(dto.getReplyId());
        comment.setLikeCount(0);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setVisible(Boolean.TRUE);

        commentRepository.save(comment);

        return "You commented";
    }

    public String update(Integer commentId, CommentUpdateDTO dto) {
        Optional<CommentEntity> optional = commentRepository.findByIdAndVisibleTrue(commentId);

        if(optional.isEmpty()) {
            throw new ItemNotFoundException("Comment not found");
        }

        CommentEntity comment = optional.get();
        if(!comment.getProfileId().equals(SpringSecurityUtil.getCurrentProfileId())){
            throw new AppBadRequestException("You can only update yours");
        }

        comment.setContent(dto.getContent());
        comment.setUpdateDate(LocalDateTime.now());
        commentRepository.save(comment);

        return "Successfully updated";
    }

    public String delete(Integer commentId) {
        Optional<CommentEntity> optional = commentRepository.findByIdAndVisibleTrue(commentId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Comment not found");
        }
        CommentEntity comment = optional.get();

        if(comment.getProfileId().equals(SpringSecurityUtil.getCurrentProfileId()) ||
           SpringSecurityUtil.getCurrentProfileRoles().contains("ADMIN")){
            int effRow = commentRepository.changeVisibleById(commentId);
            if(effRow > 0){
                return "Successfully deleted";
            }
            else {
                throw new AppBadRequestException("Something went wrong");
            }
        }
        throw new AppBadRequestException("You can only delete your comment");
    }

    public List<CommentReplyResponseDTO> getReplies(Integer commentId) {
        List<CommentRepliesMapper> repliesMapper = commentRepository.getReplies(commentId);

        return repliesMapper
                .stream()
                .map(this::replyMapperToDto)
                .toList();
    }

    public List<CommentArticleDTO> getArticleComments(String articleId) {
        articleService.articleExists(articleId);

        List<CommentArticleMapper> comments = commentRepository.getArticleComments(articleId);

        List<CommentArticleDTO> response = new LinkedList<>();
        for (CommentArticleMapper comment : comments) {
            CommentArticleDTO dto = new CommentArticleDTO();
            if(comment.getId() != null) dto.setId(comment.getId());
            if(comment.getCreatedDate() != null) dto.setCreatedDate(comment.getCreatedDate());
            if(comment.getUpdatedDate() != null) dto.setUpdatedDate(comment.getUpdatedDate());
            if(comment.getContent() != null) dto.setContent(comment.getContent());
            if(comment.getArticleId() != null) dto.setArticleId(comment.getArticleId());
            if(comment.getLikeCount() != null) dto.setLikeCount(comment.getLikeCount());
            if(comment.getProfileId() != null) dto.setProfileDto(new CommentProfileDTO(
                    comment.getProfileId(),
                    comment.getName(),
                    null,
                    attachService.openDTO(comment.getPhotoId())

            ));

            response.add(dto);
        }

        return response;

    }

    public Page<CommentFilterFullDTO> filter(CommentFilterDTO dto, Integer page, Integer size) {
        Page<Object[]> pages = customRepository.filter(dto, page, size);

        List<CommentFilterFullDTO> response = new LinkedList<>();
        for (Object[] o : pages) {
            CommentFilterFullDTO cur = new CommentFilterFullDTO();
            cur.setId((Integer) o[0]);
            cur.setCreatedDate((LocalDateTime) o[1]);
            cur.setUpdatedDate((LocalDateTime) o[2]);
            cur.setContent((String) o[3]);
            cur.setReplyId((Integer) o[4]);
            cur.setVisible((Boolean) o[5]);
            cur.setProfileDto(new CommentProfileDTO((Integer) o[6], (String) o[7], (String) o[8], null));
            cur.setArticleDto(new ArticleShortDTO((String) o[9], (String) o[10]));
            cur.setLikeCount((Integer) o[11]);

            response.add(cur);
        }

        return new PageImpl<>(response, PageRequest.of(page, size), pages.getTotalElements());
    }

    public void commentExists(Integer commentId) {
        commentRepository.findByIdAndVisibleTrue(commentId)
                .orElseThrow(() -> new ItemNotFoundException("Comment not found"));
    }

    private CommentReplyResponseDTO replyMapperToDto(CommentRepliesMapper mapper){
        CommentReplyResponseDTO dto = new CommentReplyResponseDTO();
        if(mapper.getId() != null) dto.setId(mapper.getId());
        if(mapper.getCreatedDate() != null) dto.setCreatedDate(mapper.getCreatedDate());
        if(mapper.getUpdatedDate() != null) dto.setUpdatedDate(mapper.getUpdatedDate());
        if(mapper.getLikeCount() != null) dto.setLikeCount(mapper.getLikeCount());
        if(mapper.getProfileId() != null) dto.setProfileDto(new CommentProfileDTO(
                mapper.getProfileId(),
                mapper.getName(),
                mapper.getSurname(),
                null
        ));

        return dto;
    }
}
