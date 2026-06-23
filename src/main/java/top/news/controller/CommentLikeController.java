package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.service.CommentLikeService;

@RestController
@RequestMapping("/api/v1/comment-like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like/{commentId}")
    public ResponseEntity<String> like(@PathVariable Integer commentId){
        return ResponseEntity.ok(commentLikeService.like(commentId));
    }
}
