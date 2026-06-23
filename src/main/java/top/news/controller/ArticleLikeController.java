package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.news.service.ArticleLikeService;

@RestController
@RequestMapping("/api/v1/article-like")
@CrossOrigin(origins = "*")
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
