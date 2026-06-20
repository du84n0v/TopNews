package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.entity.ArticleLikeEntity;
import top.news.enums.LikeStatusEnum;
import top.news.repository.ArticleLikeRepository;
import top.news.util.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleService articleService;

    public String like(String articleId) {
        check(articleId);
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);

        ArticleLikeEntity entity = null;
        if(optional.isPresent()){
            entity = optional.get();
        }

        if(entity == null){
            entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatusEnum.LIKE);
            entity.setCreatedDate(LocalDateTime.now());

            articleLikeRepository.save(entity);

            return "You like this article";
        }
        else{
            if(entity.getStatus().equals(LikeStatusEnum.LIKE)){
                articleLikeRepository.delete(entity);
                return "like is undo";
            }
            else{
                entity.setStatus(LikeStatusEnum.DISLIKE);
                articleLikeRepository.save(entity);
                return "You like it";
            }
        }
    }

    public String dislike(String articleId) {
        check(articleId);
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);

        ArticleLikeEntity entity = null;

        if(optional.isPresent()){
            entity = optional.get();
        }

        if(entity == null){
            entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatusEnum.DISLIKE);
            entity.setCreatedDate(LocalDateTime.now());

            articleLikeRepository.save(entity);

            return "You dislike it";
        }
        else{
            if(entity.getStatus().equals(LikeStatusEnum.DISLIKE)){
                articleLikeRepository.delete(entity);
                return "you are undo";
            }
            else{
                entity.setStatus(LikeStatusEnum.DISLIKE);
                articleLikeRepository.save(entity);
                return "You dislike it";
            }
        }

    }

    private void check(String articleId){
        articleService.articleExists(articleId);
    }
}
