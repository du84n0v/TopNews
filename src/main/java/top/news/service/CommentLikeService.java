package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.CommentLikeEntity;
import top.news.enums.LikeStatusEnum;
import top.news.repository.CommentLikeRepository;
import top.news.util.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentService commentService;

    public String like(Integer commentId) {
        check(commentId);
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByProfileIdAndCommentId(profileId, commentId);

        CommentLikeEntity entity = null;
        if(optional.isPresent()){
            entity = optional.get();
        }

        if(entity == null){
            entity = new CommentLikeEntity();
            entity.setProfileId(profileId);
            entity.setCommentId(commentId);
            entity.setStatus(LikeStatusEnum.LIKE);
            entity.setCreatedDate(LocalDateTime.now());

            commentLikeRepository.save(entity);

            return "You like it";
        }
        else {
            if(entity.getStatus().equals(LikeStatusEnum.LIKE)){
                commentLikeRepository.delete(entity);
                return "Action is undo";
            }
            else {
                entity.setStatus(LikeStatusEnum.DISLIKE);
                commentLikeRepository.save(entity);
                return "You like it";
            }
        }
    }

    public String dislike(Integer commentId) {
        check(commentId);
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByProfileIdAndCommentId(profileId, commentId);

        CommentLikeEntity entity = null;
        if(optional.isPresent()){
            entity = optional.get();
        }

        if(entity == null){
            entity = new CommentLikeEntity();
            entity.setProfileId(profileId);
            entity.setCommentId(commentId);
            entity.setStatus(LikeStatusEnum.DISLIKE);
            entity.setCreatedDate(LocalDateTime.now());

            commentLikeRepository.save(entity);

            return "You like it";
        }
        else {
            if(entity.getStatus().equals(LikeStatusEnum.DISLIKE)){
                commentLikeRepository.delete(entity);
                return "Undo";
            }
            else {
                entity.setStatus(LikeStatusEnum.LIKE);
                commentLikeRepository.save(entity);

                return "You dislike it";
            }
        }
    }

    private void check(Integer commentId){
        commentService.commentExists(commentId);
    }
}
