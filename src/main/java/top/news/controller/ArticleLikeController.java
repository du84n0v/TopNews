package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.service.ArticleLikeService;

@RestController
@RequestMapping("/article-like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like/{articleId}")
    public ResponseEntity<String> like(@PathVariable String articleId){
        return ResponseEntity.ok(articleLikeService.like(articleId));
    }

    @PostMapping("/dislike/{articleId}")
    public ResponseEntity<String> dislike(@PathVariable String articleId){
        return ResponseEntity.ok(articleLikeService.dislike(articleId));
    }
}
